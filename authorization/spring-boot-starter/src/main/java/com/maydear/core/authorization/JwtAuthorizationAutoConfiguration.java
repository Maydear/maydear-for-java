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

import com.maydear.core.authorization.jwt.JwtAuthenticationServiceImpl;
import com.maydear.core.authorization.jwt.JwtAuthorizationServiceImpl;
import com.maydear.core.authorization.jwt.JwtOptions;
import com.maydear.core.authorization.memory.MemoryTicketStoreAutoConfiguration;
import com.maydear.core.authorization.redis.RedisTicketStoreAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * JWT授权认证自动装配
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Configuration
@EnableConfigurationProperties(JwtAuthorizationProperties.class)
@ConditionalOnClass({JwtAuthorizationServiceImpl.class, JwtAuthenticationServiceImpl.class})
@Import({MemoryTicketStoreAutoConfiguration.class, RedisTicketStoreAutoConfiguration.class})
@AutoConfigureAfter({MemoryTicketStoreAutoConfiguration.class, RedisTicketStoreAutoConfiguration.class})
public class JwtAuthorizationAutoConfiguration {

    /**
     * 创建Jwt授权服务
     *
     * @return 返回Jwt授权服务
     */
    @Bean
    @ConditionalOnMissingBean(JwtAuthorizationServiceImpl.class)
    public JwtAuthorizationServiceImpl jwtAuthorizationService(JwtOptions jwtOptions, TicketStore ticketStore) {
        JwtAuthorizationServiceImpl jwtAuthorizationService = new JwtAuthorizationServiceImpl(jwtOptions, ticketStore);
        AuthorizationServiceFactory.register(jwtAuthorizationService);
        return jwtAuthorizationService;
    }

    /**
     * 创建Jwt认证服务
     *
     * @return 返回Jwt认证服务
     */
    @Bean
    @ConditionalOnMissingBean(JwtAuthenticationServiceImpl.class)
    public JwtAuthenticationServiceImpl jwtAuthenticationService(JwtOptions jwtOptions, TicketStore ticketStore) {
        JwtAuthenticationServiceImpl jwtAuthenticationService = new JwtAuthenticationServiceImpl(jwtOptions, ticketStore);
        AuthenticationServiceFactory.register(jwtAuthenticationService);
        return jwtAuthenticationService;
    }

    @Bean
    @ConditionalOnMissingBean(JwtOptions.class)
    public JwtOptions jwtOptions(JwtAuthorizationProperties properties) {
        JwtOptions options = new JwtOptions();
        options.setIssuer(properties.getIssuer());
        options.setSecret(properties.getSecret());
        options.setExpired(properties.getExpired());
        return options;
    }

}
