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

import com.maydear.core.authorization.customize.CustomizeTokenAuthenticationServiceImpl;
import com.maydear.core.authorization.customize.CustomizeTokenAuthorizationServiceImpl;
import com.maydear.core.authorization.memory.MemoryTicketStoreAutoConfiguration;
import com.maydear.core.authorization.redis.RedisTicketStoreAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 装配CustomizeTokenAuthorizationService实现
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Configuration
@ConditionalOnClass({CustomizeTokenAuthorizationServiceImpl.class, CustomizeTokenAuthenticationServiceImpl.class})
@ConditionalOnMissingClass
@Import({MemoryTicketStoreAutoConfiguration.class, RedisTicketStoreAutoConfiguration.class})
@AutoConfigureAfter({MemoryTicketStoreAutoConfiguration.class, RedisTicketStoreAutoConfiguration.class})
public class CustomizeAuthorizationAutoConfiguration {

    /**
     * 创建Jwt授权服务
     *
     * @return 返回Jwt授权服务
     */
    @Bean
    @ConditionalOnMissingBean(CustomizeTokenAuthorizationServiceImpl.class)
    public CustomizeTokenAuthorizationServiceImpl customizeTokenAuthorizationService(TicketStore ticketStore) {
        CustomizeTokenAuthorizationServiceImpl customizeTokenAuthorizationService = new CustomizeTokenAuthorizationServiceImpl(ticketStore);
        AuthorizationServiceFactory.register(customizeTokenAuthorizationService);
        return customizeTokenAuthorizationService;
    }

    /**
     * 创建Jwt授权服务
     *
     * @return 返回Jwt授权服务
     */
    @Bean
    @ConditionalOnMissingBean(CustomizeTokenAuthenticationServiceImpl.class)
    public CustomizeTokenAuthenticationServiceImpl customizeTokenAuthenticationService(TicketStore ticketStore) {
        CustomizeTokenAuthenticationServiceImpl customizeTokenAuthenticationService = new CustomizeTokenAuthenticationServiceImpl(ticketStore);
        AuthenticationServiceFactory.register(customizeTokenAuthenticationService);
        return customizeTokenAuthenticationService;
    }
}
