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
 * 补码方式
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Getter
public enum PaddingMode {

    /**
     * NoPadding
     */
    NONE(1,"NoPadding"),

    /**
     * PKCS5Padding
     */
    PKCS5(2,"PKCS5Padding"),

    /**
     * PKCS7Padding
     */
    PKCS7(3,"PKCS7Padding"),

    /**
     * ZeroPadding
     */
    ZERO(4,"ZeroPadding"),

    /**
     * ISO10126Padding
     */
    ISO10126(5,"ISO10126Padding");

    /**
     * 补码值
     */
    private int value;

    /**
     * 补码名称
     */
    private String name;

    PaddingMode(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
