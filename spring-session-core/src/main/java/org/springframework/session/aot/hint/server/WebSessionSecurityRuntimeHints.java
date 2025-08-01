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

package org.springframework.session.aot.hint.server;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.security.web.server.csrf.DefaultCsrfToken;
import org.springframework.util.ClassUtils;

/**
 * {@link RuntimeHintsRegistrar} for Reactive Session hints.
 *
 * @author Marcus Da Coregio
 */
class WebSessionSecurityRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		if (!ClassUtils.isPresent("org.springframework.web.server.WebSession", classLoader) || !ClassUtils
			.isPresent("org.springframework.security.web.server.csrf.DefaultCsrfToken", classLoader)) {
			return;
		}
		hints.serialization().registerType(DefaultCsrfToken.class);
	}

}
