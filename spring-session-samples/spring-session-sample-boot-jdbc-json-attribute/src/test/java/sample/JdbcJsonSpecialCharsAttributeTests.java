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

package sample;

import org.springframework.test.context.TestPropertySource;

/**
 * Test that special characters in usernames work.
 *
 * @author Rob Winch
 */
@TestPropertySource(properties = "spring.security.user.name=rÃ¼diger")
class JdbcJsonSpecialCharsAttributeTests extends JdbcJsonAttributeTests {

}
