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

package org.springframework.session.config;

import org.springframework.session.SessionRepository;

/**
 * Strategy that can be used to customize the {@link SessionRepository} before it is fully
 * initialized, in particular to tune its configuration.
 *
 * @param <T> the {@link SessionRepository} type
 * @author Vedran Pavic
 * @since 2.2.0
 */
@FunctionalInterface
public interface SessionRepositoryCustomizer<T extends SessionRepository> {

	/**
	 * Customize the {@link SessionRepository}.
	 * @param sessionRepository the {@link SessionRepository} to customize
	 */
	void customize(T sessionRepository);

}
