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

package sample.config;

import java.io.InputStream;

import com.maxmind.geoip2.DatabaseReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Rob Winch
 *
 */
@Configuration
public class GeoConfig {

	@Bean
	public DatabaseReader geoDatabaseReader(@Value("classpath:GeoLite2-City.mmdb") InputStream geoInputStream)
			throws Exception {
		return new DatabaseReader.Builder(geoInputStream).build();
	}

}
