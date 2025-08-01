/*
 * Copyright 2014-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.session.jdbc.aot.hint;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.aot.hint.predicate.RuntimeHintsPredicates;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.SpringFactoriesLoader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SessionJdbcRuntimeHints}
 *
 * @author Marcus Da Coregio
 */
class SessionJdbcRuntimeHintsTests {

	private final RuntimeHints hints = new RuntimeHints();

	private final SessionJdbcRuntimeHints sessionJdbcRuntimeHints = new SessionJdbcRuntimeHints();

	@Test
	void aotFactoriesContainsRegistrar() {
		boolean match = SpringFactoriesLoader.forResourceLocation("META-INF/spring/aot.factories")
			.load(RuntimeHintsRegistrar.class)
			.stream()
			.anyMatch((registrar) -> registrar instanceof SessionJdbcRuntimeHints);
		assertThat(match).isTrue();
	}

	@ParameterizedTest
	@MethodSource("getSchemaFileNames")
	void jdbcSchemasHasHints(String schemaFileName) {
		this.sessionJdbcRuntimeHints.registerHints(this.hints, getClass().getClassLoader());
		assertThat(RuntimeHintsPredicates.resource().forResource("org/springframework/session/jdbc/" + schemaFileName))
			.accepts(this.hints);
	}

	private static Stream<String> getSchemaFileNames() throws IOException {
		return Arrays
			.stream(new PathMatchingResourcePatternResolver()
				.getResources("classpath*:org/springframework/session/jdbc/schema-*.sql"))
			.map(Resource::getFilename);
	}

	@Test
	void dataSourceHasHints() {
		this.sessionJdbcRuntimeHints.registerHints(this.hints, getClass().getClassLoader());
		assertThat(RuntimeHintsPredicates.reflection().onType(TypeReference.of("javax.sql.DataSource")))
			.accepts(this.hints);
	}

}
