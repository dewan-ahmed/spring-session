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

package org.springframework.session.security.web.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Tests for {@link SpringSessionRememberMeServices}.
 *
 * @author Vedran Pavic
 */
class SpringSessionRememberMeServicesTests {

	private SpringSessionRememberMeServices rememberMeServices;

	@Test
	void create() {
		this.rememberMeServices = new SpringSessionRememberMeServices();
		assertThat(ReflectionTestUtils.getField(this.rememberMeServices, "rememberMeParameterName"))
			.isEqualTo("remember-me");
		assertThat(ReflectionTestUtils.getField(this.rememberMeServices, "alwaysRemember")).isEqualTo(false);
		assertThat(ReflectionTestUtils.getField(this.rememberMeServices, "validitySeconds")).isEqualTo(2592000);
	}

	@Test
	void createWithCustomParameter() {
		this.rememberMeServices = new SpringSessionRememberMeServices();
		this.rememberMeServices.setRememberMeParameterName("test-param");
		assertThat(ReflectionTestUtils.getField(this.rememberMeServices, "rememberMeParameterName"))
			.isEqualTo("test-param");
	}

	@Test
	void createWithNullParameter() {
		this.rememberMeServices = new SpringSessionRememberMeServices();
		assertThatIllegalArgumentException().isThrownBy(() -> this.rememberMeServices.setRememberMeParameterName(null))
			.withMessage("rememberMeParameterName cannot be empty or null");
	}

	@Test
	void createWithAlwaysRemember() {
		this.rememberMeServices = new SpringSessionRememberMeServices();
		this.rememberMeServices.setAlwaysRemember(true);
		assertThat(ReflectionTestUtils.getField(this.rememberMeServices, "alwaysRemember")).isEqualTo(true);
	}

	@Test
	void createWithCustomValidity() {
		this.rememberMeServices = new SpringSessionRememberMeServices();
		this.rememberMeServices.setValiditySeconds(100000);
		assertThat(ReflectionTestUtils.getField(this.rememberMeServices, "validitySeconds")).isEqualTo(100000);
	}

	@Test
	void autoLogin() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		this.rememberMeServices = new SpringSessionRememberMeServices();
		this.rememberMeServices.autoLogin(request, response);
		verifyNoMoreInteractions(request, response);
	}

	// gh-752
	@Test
	void loginFailRemoveSecurityContext() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		given(request.getSession(eq(false))).willReturn(session);
		this.rememberMeServices = new SpringSessionRememberMeServices();
		this.rememberMeServices.loginFail(request, response);
		verify(request, times(1)).getSession(eq(false));
		verify(session, times(1)).removeAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		verifyNoMoreInteractions(request, response, session);
	}

	@Test
	void loginSuccess() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		Authentication authentication = mock(Authentication.class);
		HttpSession session = mock(HttpSession.class);
		given(request.getParameter(eq("remember-me"))).willReturn("true");
		given(request.getSession()).willReturn(session);
		this.rememberMeServices = new SpringSessionRememberMeServices();
		this.rememberMeServices.loginSuccess(request, response, authentication);
		verify(request, times(1)).getParameter(eq("remember-me"));
		verify(request, times(1)).getSession();
		verify(request, times(1)).setAttribute(eq(SpringSessionRememberMeServices.REMEMBER_ME_LOGIN_ATTR), eq(true));
		verify(session, times(1)).setMaxInactiveInterval(eq(2592000));
		verifyNoMoreInteractions(request, response, session, authentication);
	}

	@Test
	void loginSuccessWithCustomParameter() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		Authentication authentication = mock(Authentication.class);
		HttpSession session = mock(HttpSession.class);
		given(request.getParameter(eq("test-param"))).willReturn("true");
		given(request.getSession()).willReturn(session);
		this.rememberMeServices = new SpringSessionRememberMeServices();
		this.rememberMeServices.setRememberMeParameterName("test-param");
		this.rememberMeServices.loginSuccess(request, response, authentication);
		verify(request, times(1)).getParameter(eq("test-param"));
		verify(request, times(1)).getSession();
		verify(request, times(1)).setAttribute(eq(SpringSessionRememberMeServices.REMEMBER_ME_LOGIN_ATTR), eq(true));
		verify(session, times(1)).setMaxInactiveInterval(eq(2592000));
		verifyNoMoreInteractions(request, response, session, authentication);
	}

	@Test
	void loginSuccessWithAlwaysRemember() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		Authentication authentication = mock(Authentication.class);
		HttpSession session = mock(HttpSession.class);
		given(request.getSession()).willReturn(session);
		this.rememberMeServices = new SpringSessionRememberMeServices();
		this.rememberMeServices.setAlwaysRemember(true);
		this.rememberMeServices.loginSuccess(request, response, authentication);
		verify(request, times(1)).getSession();
		verify(request, times(1)).setAttribute(eq(SpringSessionRememberMeServices.REMEMBER_ME_LOGIN_ATTR), eq(true));
		verify(session, times(1)).setMaxInactiveInterval(eq(2592000));
		verifyNoMoreInteractions(request, response, session, authentication);
	}

	@Test
	void loginSuccessWithCustomValidity() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		Authentication authentication = mock(Authentication.class);
		HttpSession session = mock(HttpSession.class);
		given(request.getParameter(eq("remember-me"))).willReturn("true");
		given(request.getSession()).willReturn(session);
		this.rememberMeServices = new SpringSessionRememberMeServices();
		this.rememberMeServices.setValiditySeconds(100000);
		this.rememberMeServices.loginSuccess(request, response, authentication);
		verify(request, times(1)).getParameter(eq("remember-me"));
		verify(request, times(1)).getSession();
		verify(request, times(1)).setAttribute(eq(SpringSessionRememberMeServices.REMEMBER_ME_LOGIN_ATTR), eq(true));
		verify(session, times(1)).setMaxInactiveInterval(eq(100000));
		verifyNoMoreInteractions(request, response, session, authentication);
	}

}
