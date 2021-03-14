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
package com.maydear.core.authorization.redis;

import com.maydear.core.authorization.AuthorizationIdentity;
import com.maydear.core.authorization.TicketStore;
import com.maydear.core.authorization.store.redis.RedisAuthorizationOptions;
import com.maydear.core.authorization.store.redis.TicketStoreRedisImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.net.UnknownHostException;

/**
 * Redis配置选项自动装配
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RedisAuthorizationProperties.class)
@ConditionalOnClass(RedisOperations.class)
@Import({LettuceConnectionConfiguration.class})
public class RedisTicketStoreAutoConfiguration {

    /**
     * 创建内存配置选项
     *
     * @param properties 配置信息
     * @return 返回内存配置选项
     */
    @Bean
    public RedisAuthorizationOptions redisAuthorizationOptions(RedisAuthorizationProperties properties) {
        RedisAuthorizationOptions options = new RedisAuthorizationOptions();
        options.setExpired(properties.getExpired());
        return options;
    }

    /**
     * 创建AccessToken仓储
     *
     * @return 返回AccessToken仓储实现
     */
    @Bean
    @ConditionalOnMissingBean(TicketStore.class)
    public TicketStore accessTokenStore(RedisTemplate<String, AuthorizationIdentity> redisTemplate, RedisAuthorizationOptions redisAuthorizationOptions) {
        return new TicketStoreRedisImpl(redisTemplate, redisAuthorizationOptions);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, AuthorizationIdentity> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<String, AuthorizationIdentity> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
