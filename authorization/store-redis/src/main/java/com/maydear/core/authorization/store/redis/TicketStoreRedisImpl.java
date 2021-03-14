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
package com.maydear.core.authorization.store.redis;

import com.maydear.core.authorization.AuthorizationIdentity;
import com.maydear.core.authorization.TicketStore;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Redis令牌仓储类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class TicketStoreRedisImpl implements TicketStore {

    private RedisTemplate<String, AuthorizationIdentity> cache;
    private RedisAuthorizationOptions options;

    public TicketStoreRedisImpl(RedisTemplate<String, AuthorizationIdentity> cache, RedisAuthorizationOptions options) {
        this.cache = cache;
        this.options = options;
    }

    @Override
    public void store(AuthorizationIdentity authorizationIdentity) {
        cache.boundValueOps(authorizationIdentity.getIdentity().toString()).set(authorizationIdentity, options.getExpired(), TimeUnit.SECONDS);
    }

    @Override
    public AuthorizationIdentity retrieve(String key) {
        AuthorizationIdentity authorizationIdentity = cache.boundValueOps(key).get();
        if (ObjectUtils.isNotEmpty(authorizationIdentity)) {
            cache.expire(key, options.getExpired(), TimeUnit.SECONDS);
        }
        return authorizationIdentity;
    }

    @Override
    public void remove(String key) {
        cache.delete(key);
    }
}
