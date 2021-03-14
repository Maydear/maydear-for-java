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
package com.maydear.core.framework.util;

import lombok.Getter;

/**
 * 密码模式
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Getter
public enum CipherMode {
    /**
     * CBC 模式
     */
    CBC(1,"CBC"),

    /**
     * ECB 模式
     */
    ECB(2,"ECB"),

    /**
     * OFB 模式
     */
    OFB(3,"OFB"),

    /**
     * CFB 模式
     */
    CFB(4,"CFB"),

    /**
     * CTS 模式
     */
    CTS(5,"CTS");

    /**
     * 模式值
     */
    private int value;
    /**
     * 模式名称
     */
    private String name;

    /**
     * 密码模式枚举
     * @param value 枚举值
     * @param name 枚举名
     */
    CipherMode(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
