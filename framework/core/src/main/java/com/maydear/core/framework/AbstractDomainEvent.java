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
package com.maydear.core.framework;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 领域事件
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@AllArgsConstructor
public abstract class AbstractDomainEvent implements Serializable {

    private static final long serialVersionUID = -6021499164277404862L;

    /**
     * 事件参数
     */
    @Getter
    private Object args;

    /**
     * 事件名称
     */
    @Getter
    private String name;

    /**
     * 事件唯一id
     */
    @Getter
    private UUID id;

    /**
     * 创建事件
     */
    @Getter
    private LocalDateTime createTime;



}
