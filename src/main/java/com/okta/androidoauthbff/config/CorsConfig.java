/*
 * Copyright 2020-2024 the original author or authors.
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
package com.okta.androidoauthbff.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration(proxyBeanMethods = false)
public class CorsConfig {

	@Value("${app.base-uri}")
	private String appBaseUri;

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final var config = new CorsConfiguration();
		config.addAllowedHeader("X-XSRF-TOKEN");
		config.addAllowedHeader(HttpHeaders.CONTENT_TYPE);
		config.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedOrigins(Collections.singletonList(this.appBaseUri));
		config.setAllowCredentials(true);

		final var configurationSource = new UrlBasedCorsConfigurationSource();
		configurationSource.registerCorsConfiguration("/**", config);
		return configurationSource;
	}

}
