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
package com.maydear.core.framework.spring.web.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maydear.core.framework.jackson.mapper.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Json序列化对象
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Configuration
public class JsonConfigurer {

    /**
     * 注入ObjectMapper
     * @return 返回ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.INSTANCE.getMapper();
    }
}