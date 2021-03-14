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
package com.maydear.core.authorization;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Map;

/**
 * 认证服务工厂
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class AuthenticationServiceFactory {

    private static Map<String, AuthenticationService> authenticationServiceMap = Maps.newConcurrentMap();

    public static void register(AuthenticationService authenticationService) {
        if (!authenticationServiceMap.containsKey(authenticationService.getClass())) {
            authenticationServiceMap.put(authenticationService.getScheme(), authenticationService);
        }
    }

    /**
     * 获取认证服务工厂
     * @param schemeName 认证架构名称
     * @return 获取认证服务
     */
    public static AuthenticationService getAuthenticationService(String schemeName) {
        if (MapUtils.isEmpty(authenticationServiceMap)) {
            throw new NotImplementedException("AuthenticationService");
        }

        if (authenticationServiceMap.containsKey(schemeName)) {
            return authenticationServiceMap.get(schemeName);
        } else {
            return getAuthenticationService();
        }
    }

    /**
     * 获取认证服务工厂
     * @return 获取认证服务
     */
    public static AuthenticationService getAuthenticationService() {
        if (MapUtils.isEmpty(authenticationServiceMap)) {
            throw new NotImplementedException("AuthenticationService");
        }
        return authenticationServiceMap.values().stream().findFirst().orElse(null);
    }
}
