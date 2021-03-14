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


/**
 * 令牌类授权服务类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public interface AuthorizationService {

    /**
     * 认证架构
     *
     * @return 返回认证头前缀
     */
    String getScheme();

    /**
     * 根据票据信息获取认证用户信息
     *
     * @param ticket 认证票据
     * @return 返回当前认证用户信息
     */
    AuthorizationIdentity getAuthorizationIdentity(String ticket);
}
