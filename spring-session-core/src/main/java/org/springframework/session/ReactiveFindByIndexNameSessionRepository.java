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

package org.springframework.session;

import java.util.Map;

import reactor.core.publisher.Mono;

/**
 * Allow finding sessions by the specified index name and index value.
 *
 * @param <S> the type of Session being managed by this
 * {@link ReactiveFindByIndexNameSessionRepository}
 * @author Marcus da Coregio
 * @since 3.3
 */
public interface ReactiveFindByIndexNameSessionRepository<S extends Session> {

	/**
	 * A session index that contains the current principal name (i.e. username).
	 * <p>
	 * It is the responsibility of the developer to ensure the index is populated since
	 * Spring Session is not aware of the authentication mechanism being used.
	 */
	String PRINCIPAL_NAME_INDEX_NAME = "PRINCIPAL_NAME_INDEX_NAME";

	/**
	 * Find a {@link Map} of the session id to the {@link Session} of all sessions that
	 * contain the specified index name index value.
	 * @param indexName the name of the index (i.e. {@link #PRINCIPAL_NAME_INDEX_NAME})
	 * @param indexValue the value of the index to search for.
	 * @return a {@code Map} (never {@code null}) of the session id to the {@code Session}
	 */
	Mono<Map<String, S>> findByIndexNameAndIndexValue(String indexName, String indexValue);

	/**
	 * A shortcut for {@link #findByIndexNameAndIndexValue(String, String)} that uses
	 * {@link #PRINCIPAL_NAME_INDEX_NAME} for the index name.
	 * @param principalName the principal name
	 * @return a {@code Map} (never {@code null}) of the session id to the {@code Session}
	 */
	default Mono<Map<String, S>> findByPrincipalName(String principalName) {
		return findByIndexNameAndIndexValue(PRINCIPAL_NAME_INDEX_NAME, principalName);
	}

}
