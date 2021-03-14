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

import com.maydear.core.authorization.AbstractAuthorizationOptions;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 内存Token选项
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemoryCacheAuthorizationOptions extends AbstractAuthorizationOptions implements Serializable {

    /**
     * 默认初始容量
     */
    public static final int DEFAULT_CAPACITY = 100;

    /**
     * 默认最大缓存
     */
    public static final int DEFAULT_MAXIMUM_SIZE = 2000;

    /**
     * 初始容量
     */
    private Integer initialCapacity;

    /**
     * 最大缓存
     */
    private Integer maximumSize;
}
