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
package com.maydear.core.authorization.spring.security.util;

import com.maydear.core.authorization.AuthenticationScheme;
import com.maydear.core.authorization.AuthenticationService;
import com.maydear.core.authorization.AuthenticationServiceFactory;

/**
 * 认证工具类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public final class AuthenticationUtils {

    /**
     * 证书认证
     *
     * @return 返回认证服务
     */
    public static AuthenticationService getCertificateAuthentication() {
        return AuthenticationServiceFactory.getAuthenticationService(AuthenticationScheme.CERTIFICATE_SCHEME_NAME);
    }

    /**
     * Jwt令牌认证
     *
     * @return 返回认证服务
     */
    public static AuthenticationService getJwtAuthentication() {
        return AuthenticationServiceFactory.getAuthenticationService(AuthenticationScheme.JWT_SCHEME_NAME);
    }

    /**
     * 自定义令牌认证
     *
     * @return 返回认证服务
     */
    public static AuthenticationService getCustomizeAuthentication() {
        return AuthenticationServiceFactory.getAuthenticationService(AuthenticationScheme.CUSTOMIZE_SCHEME_NAME);
    }

    /**
     * 返回默认认证
     * @return 返回认证服务
     */
    public static AuthenticationService getDefaultAuthentication() {
        return AuthenticationServiceFactory.getAuthenticationService();
    }
}
