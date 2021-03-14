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
package com.maydear.core.authorization.spring.security;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


/**
 * 匠人认证令牌
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class TokenTicketAuthentication implements Authentication {

    private static final int AUTHORIZATION_HEADER_LENGTH = 2;

    private String name;
    private String credential;

    private TokenTicketAuthentication(String name, String credential) {
        this.name = name;
        this.credential = credential;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public static TokenTicketAuthentication build(String authorizationValue) {
        String[] authorizationValueList = StringUtils.split(authorizationValue, " ");
        if (ArrayUtils.isNotEmpty(authorizationValueList) && authorizationValueList.length == AUTHORIZATION_HEADER_LENGTH) {
            return new TokenTicketAuthentication(authorizationValueList[0], authorizationValueList[1]);
        }
        return null;
    }

    @Override
    public Object getCredentials() {
        return credential;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return name;
    }
}
