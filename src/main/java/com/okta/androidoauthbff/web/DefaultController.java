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
package com.okta.androidoauthbff.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.function.ServerRequest;

@Controller
public class DefaultController {

	@Value("${app.base-uri}")
	private String appBaseUri;

	@GetMapping("/")
	public String root() {
		return "redirect:" + this.appBaseUri;
	}

	private void getOauthInfo(ServerRequest request, String clientRegistrationId) {
		Authentication principal = (Authentication) request.servletRequest().getUserPrincipal();
		OAuth2AuthorizedClientRepository authorizedClientRepository = getApplicationContext(request)
				.getBean(OAuth2AuthorizedClientRepository.class);
		OAuth2AuthorizedClient authorizedClient = authorizedClientRepository.loadAuthorizedClient(
				clientRegistrationId, principal, request.servletRequest());
		if (authorizedClient != null) {
			OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
			ServerRequest bearerRequest = ServerRequest.from(request)
					.headers(httpHeaders -> httpHeaders.setBearerAuth(accessToken.getTokenValue())).build();
		}
	}

}
