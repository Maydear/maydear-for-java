/*
 * Copyright 2008-2021 the original author or authors.
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
package com.maydear.core.authorization.spring.security.provider;

import com.maydear.core.authorization.AuthorizationIdentity;
import com.maydear.core.authorization.AuthorizationService;
import com.maydear.core.authorization.AuthorizationServiceFactory;
import com.maydear.core.authorization.spring.security.AuthorizationIdentityAuthentication;
import com.maydear.core.authorization.spring.security.TokenTicketAuthentication;
import com.maydear.core.authorization.spring.security.exception.UnAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 封装的认证驱动
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Slf4j
@Component
public class MaydearAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String authenticationScheme = authentication.getName();
        String ticket = (String) authentication.getCredentials();

        AuthorizationService authorizationService =  AuthorizationServiceFactory.getAuthorizationService(authenticationScheme);
        if (!StringUtils.equalsIgnoreCase(authenticationScheme, authorizationService.getScheme())) {
            throw new UnAuthenticationException();
        }
        AuthorizationIdentity authorizationIdentity = authorizationService.getAuthorizationIdentity(ticket);

        if (ObjectUtils.isEmpty(authorizationIdentity)) {
            throw new UnAuthenticationException();
        }

        return new AuthorizationIdentityAuthentication(authorizationIdentity);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(TokenTicketAuthentication.class);
    }
}
