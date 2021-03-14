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
 * 授权服务工厂
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class AuthorizationServiceFactory {

    private static Map<String, AuthorizationService> authorizationServiceMap = Maps.newConcurrentMap();

    /**
     * 注册授权服务
     * @param authorizationService 授权服务
     */
    public static void register(AuthorizationService authorizationService) {
        if (!authorizationServiceMap.containsKey(authorizationService.getScheme())) {
            authorizationServiceMap.put(authorizationService.getScheme(), authorizationService);
        }
    }


    /**
     * 获取授权服务
     * @param schemeName 认证架构名称
     * @return 返回授权服务
     */
    public static AuthorizationService getAuthorizationService(String schemeName) {
        if (MapUtils.isEmpty(authorizationServiceMap)) {
            throw new NotImplementedException("AuthorizationService not find Implemented");
        }

        if (authorizationServiceMap.containsKey(schemeName)) {
            return authorizationServiceMap.get(schemeName);
        } else {
            return getAuthorizationService();
        }
    }

    /**
     * 获取授权服务
     * @return 返回授权服务
     */
    public static AuthorizationService getAuthorizationService() {
        if (MapUtils.isEmpty(authorizationServiceMap)) {
            throw new NotImplementedException("AuthorizationService not find Implemented");
        }
        return authorizationServiceMap.values().stream().findFirst().orElse(null);
    }
}
