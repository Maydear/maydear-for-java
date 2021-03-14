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

import com.maydear.core.authorization.certificate.CertificateAuthenticationServiceImpl;
import com.maydear.core.authorization.certificate.CertificateAuthorizationOptions;
import com.maydear.core.authorization.certificate.CertificateAuthorizationServiceImpl;
import com.maydear.core.authorization.customize.CustomizeTokenAuthorizationServiceImpl;
import com.maydear.core.authorization.jwt.JwtOptions;
import com.maydear.core.authorization.memory.MemoryTicketStoreAutoConfiguration;
import com.maydear.core.authorization.redis.RedisTicketStoreAutoConfiguration;
import org.checkerframework.checker.units.qual.C;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 装配CertificateAuthorizationService实现
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Configuration
@ConditionalOnClass({CertificateAuthorizationServiceImpl.class, CertificateAuthenticationServiceImpl.class})
@ConditionalOnMissingClass
@EnableConfigurationProperties(CertificateAuthorizationProperties.class)
@Import({MemoryTicketStoreAutoConfiguration.class, RedisTicketStoreAutoConfiguration.class})
@AutoConfigureAfter({MemoryTicketStoreAutoConfiguration.class, RedisTicketStoreAutoConfiguration.class})
public class CertificateAuthorizationAutoConfiguration {

    /**
     * 创建证书授权服务
     *
     * @return 返回证书授权服务
     */
    @Bean
    @ConditionalOnMissingBean(CertificateAuthorizationServiceImpl.class)
    public CertificateAuthorizationServiceImpl certificateAuthorizationService(CertificateAuthorizationOptions certificateAuthorizationOptions) {
        CertificateAuthorizationServiceImpl certificateAuthorizationService = new CertificateAuthorizationServiceImpl(certificateAuthorizationOptions);
        AuthorizationServiceFactory.register(certificateAuthorizationService);
        return certificateAuthorizationService;
    }

    /**
     * 创建证书授权服务
     *
     * @return 返回证书授权服务
     */
    @Bean
    @ConditionalOnMissingBean(CertificateAuthenticationServiceImpl.class)
    public CertificateAuthenticationServiceImpl certificateAuthenticationService(CertificateAuthorizationOptions certificateAuthorizationOptions) {
        CertificateAuthenticationServiceImpl certificateAuthenticationService = new CertificateAuthenticationServiceImpl(certificateAuthorizationOptions);
        AuthenticationServiceFactory.register(certificateAuthenticationService);
        return certificateAuthenticationService;
    }

    @Bean
    @ConditionalOnMissingBean(CertificateAuthorizationOptions.class)
    public CertificateAuthorizationOptions certificateAuthorizationOptions(CertificateAuthorizationProperties propertie) {
        CertificateAuthorizationOptions options = new CertificateAuthorizationOptions();
        options.setPrivateKey(propertie.getPrivateKey());
        options.setPublicKey(propertie.getPublicKey());
        return options;
    }
}
