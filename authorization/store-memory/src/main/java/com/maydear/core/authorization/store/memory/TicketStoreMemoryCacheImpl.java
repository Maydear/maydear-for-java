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
package com.maydear.core.authorization.store.memory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.maydear.core.authorization.AbstractAuthorizationOptions;
import com.maydear.core.authorization.AuthorizationIdentity;
import com.maydear.core.authorization.TicketStore;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * 本地内存令牌仓储类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class TicketStoreMemoryCacheImpl implements TicketStore {

    /**
     *
     */
    private final MemoryCacheAuthorizationOptions memoryCacheAuthorizationOptions;

    private static Cache<String, AuthorizationIdentity> cache;

    private static void setCache(Cache<String, AuthorizationIdentity> cacheInner) {
        cache = cacheInner;
    }

    @PostConstruct
    public void initCache() {
        int initialCapacity = MemoryCacheAuthorizationOptions.DEFAULT_CAPACITY;
        int maximumSize = MemoryCacheAuthorizationOptions.DEFAULT_MAXIMUM_SIZE;
        long expired = AbstractAuthorizationOptions.DEFAULT_EXPIRED;

        if (ObjectUtils.isNotEmpty(memoryCacheAuthorizationOptions) && ObjectUtils.isNotEmpty(memoryCacheAuthorizationOptions.getInitialCapacity())) {
            initialCapacity = memoryCacheAuthorizationOptions.getInitialCapacity();
        }

        if (ObjectUtils.isNotEmpty(memoryCacheAuthorizationOptions) && ObjectUtils.isNotEmpty(memoryCacheAuthorizationOptions.getMaximumSize())) {
            maximumSize = memoryCacheAuthorizationOptions.getMaximumSize();
        }

        if (ObjectUtils.isNotEmpty(memoryCacheAuthorizationOptions) && ObjectUtils.isNotEmpty(memoryCacheAuthorizationOptions.getExpired()) && memoryCacheAuthorizationOptions.getExpired() > 0) {
            expired = memoryCacheAuthorizationOptions.getExpired();
        }
        setCache(Caffeine.newBuilder()
            .initialCapacity(initialCapacity)
            .maximumSize(maximumSize)
            .expireAfterAccess(Duration.of(expired, ChronoUnit.SECONDS))
            .build());
    }


    /**
     * @param memoryCacheAuthorizationOptions 内存缓存选项
     */
    public TicketStoreMemoryCacheImpl(MemoryCacheAuthorizationOptions memoryCacheAuthorizationOptions) {
        this.memoryCacheAuthorizationOptions = memoryCacheAuthorizationOptions;
    }

    @Override
    public void store(AuthorizationIdentity authorizationIdentity) {
        if (ObjectUtils.isNotEmpty(authorizationIdentity)) {
            String key = authorizationIdentity.getIdentity().toString();
            cache.invalidate(key);
            cache.put(key, authorizationIdentity);
        }
    }

    @Override
    public AuthorizationIdentity retrieve(String key) {
        if (ObjectUtils.isNotEmpty(key)) {
            return cache.getIfPresent(key);
        }
        return null;
    }

    @Override
    public void remove(String key) {
        if (ObjectUtils.isNotEmpty(key)) {
            cache.invalidate(key);
        }
    }
}
