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

import lombok.Data;

import java.io.Serializable;

/**
 * 安全设置
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Data
public abstract class AbstractAuthorizationOptions implements Serializable {

    private static final long serialVersionUID = -1698682721548828342L;

    /**
     * 默认过期时间
     */
    public static final long DEFAULT_EXPIRED = 3600;

    /**
     * 失效时间(秒)
     */
    private Long expired;
}
