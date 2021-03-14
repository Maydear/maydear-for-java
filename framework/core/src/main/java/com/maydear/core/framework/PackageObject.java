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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RESTful APIs 统一包裹对象
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
public final class PackageObject implements Serializable {

    private static final long serialVersionUID = -6290207401155634460L;

    /**
     * 当前时间
     */
    private LocalDateTime now;

    /**
     * 请求识别号
     */
    private UUID requestId;

    /**
     * 状态码
     */
    private int statusCode;

    /**
     * 消息提示
     */
    private String notification;

    /**
     * 数据内容
     */
    private Object body;
}
