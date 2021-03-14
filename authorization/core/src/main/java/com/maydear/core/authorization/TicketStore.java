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
 * 票据存储接口
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public interface TicketStore {

    /**
     * 存储存储到token的值
     *
     * @param authorizationIdentity 授权书身份标识
     */
    void store(AuthorizationIdentity authorizationIdentity);

    /**
     * 取回存储值的值
     *
     * @param ticket 票据
     * @return
     */
    AuthorizationIdentity retrieve(String ticket);

    /**
     * 移除指定的访问令牌
     *
     * @param key 访问令牌键
     */
    void remove(String key);
}
