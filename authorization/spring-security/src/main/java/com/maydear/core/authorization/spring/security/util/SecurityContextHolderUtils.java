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

import com.maydear.core.authorization.AuthorizationIdentity;
import com.maydear.core.authorization.spring.security.AuthorizationIdentityAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全上下文工具类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public final class SecurityContextHolderUtils {

    /**
     * 静态工具类不应该被实例化
     */
    private SecurityContextHolderUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取当前授权用户身份信息，如果当前未进行授权认证或认证类型不是AuthorizationIdentity则返回null。
     * @return 返回授权身份信息
     */
    public static AuthorizationIdentity getCurrentAuthorizationIdentity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication ==null){
            return null;
        }
        if (authentication.isAuthenticated() && authentication instanceof AuthorizationIdentityAuthentication) {
            return ((AuthorizationIdentityAuthentication) authentication).getAuthorizationIdentity();
        }
        return null;
    }
}
