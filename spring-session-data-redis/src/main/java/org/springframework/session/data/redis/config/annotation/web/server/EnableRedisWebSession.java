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

package org.springframework.session.data.redis.config.annotation.web.server;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.session.MapSession;
import org.springframework.session.SaveMode;
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession;
import org.springframework.session.data.redis.ReactiveRedisSessionRepository;
import org.springframework.web.server.session.WebSessionManager;

/**
 * Add this annotation to an {@code @Configuration} class to expose the
 * {@link WebSessionManager} as a bean named {@code webSessionManager} and backed by
 * Reactive Redis. In order to leverage the annotation, a single
 * {@link ReactiveRedisConnectionFactory} must be provided. For example:
 *
 * <pre class="code">
 * &#064;Configuration(proxyBeanMethods = false)
 * &#064;EnableRedisWebSession
 * public class RedisWebSessionConfig {
 *
 *     &#064;Bean
 *     public LettuceConnectionFactory redisConnectionFactory() {
 *         return new LettuceConnectionFactory();
 *     }
 *
 * }
 * </pre>
 *
 * More advanced configurations can extend {@link RedisWebSessionConfiguration} instead.
 *
 * @author Vedran Pavic
 * @author Kai Zhao
 * @since 2.0.0
 * @see EnableSpringWebSession
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RedisWebSessionConfiguration.class)
public @interface EnableRedisWebSession {

	/**
	 * The session timeout in seconds. By default, it is set to 1800 seconds (30 minutes).
	 * A negative number means permanently valid.
	 * @return the seconds a session can be inactive before expiring
	 */
	int maxInactiveIntervalInSeconds() default MapSession.DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS;

	/**
	 * Defines a unique namespace for keys. The value is used to isolate sessions by
	 * changing the prefix from default {@code spring:session:} to
	 * {@code <redisNamespace>:}.
	 * <p>
	 * For example, if you had an application named "Application A" that needed to keep
	 * the sessions isolated from "Application B" you could set two different values for
	 * the applications and they could function within the same Redis instance.
	 * @return the unique namespace for keys
	 */
	String redisNamespace() default ReactiveRedisSessionRepository.DEFAULT_NAMESPACE;

	/**
	 * Save mode for the session. The default is {@link SaveMode#ON_SET_ATTRIBUTE}, which
	 * only saves changes made to session.
	 * @return the save mode
	 * @since 2.2.0
	 */
	SaveMode saveMode() default SaveMode.ON_SET_ATTRIBUTE;

}
