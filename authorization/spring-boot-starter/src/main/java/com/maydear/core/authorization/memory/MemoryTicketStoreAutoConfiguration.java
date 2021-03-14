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
package com.maydear.core.authorization.memory;

import com.maydear.core.authorization.TicketStore;
import com.maydear.core.authorization.store.memory.MemoryCacheAuthorizationOptions;
import com.maydear.core.authorization.store.memory.TicketStoreMemoryCacheImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 内存配置选项自动装配
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Configuration
@EnableConfigurationProperties(MemoryAuthorizationProperties.class)
@ConditionalOnClass(TicketStoreMemoryCacheImpl.class)
public class MemoryTicketStoreAutoConfiguration {

    /**
     * 创建内存配置选项
     *
     * @param properties 配置信息
     * @return 返回内存配置选项
     */
    @Bean
    public MemoryCacheAuthorizationOptions memoryCacheAuthorizationOptions(MemoryAuthorizationProperties properties) {
        MemoryCacheAuthorizationOptions options = new MemoryCacheAuthorizationOptions();
        options.setInitialCapacity(properties.getInitialCapacity());
        options.setMaximumSize(properties.getMaximumSize());
        options.setExpired(properties.getExpired());
        return options;
    }

    /**
     * 创建票据仓储
     *
     * @return 返回票据仓储实现
     */
    @Bean
    @ConditionalOnMissingBean(TicketStore.class)
    public TicketStore ticketStore(MemoryCacheAuthorizationOptions memoryCacheAuthorizationOptions) {
        return new TicketStoreMemoryCacheImpl(memoryCacheAuthorizationOptions);
    }

}
