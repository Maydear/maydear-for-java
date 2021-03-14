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

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64 工具类
 * com.google.common.base
 * (针对jdk1.8进行优化)
 *
 * @author kelvin.liang
 */
public class Base64Utils {

    /**
     * 防止静态类被实例化
     */
    private Base64Utils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 将明文字符串进行Base64编码
     *
     * @param str 需要编码的字符串
     * @return Base64编码后的字符串
     */
    public static String encode(String str) {
        return Base64.getUrlEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将Base64编码字符串转明文
     *
     * @param str Base64编码字符串
     * @return 明文字符串
     */
    public static String decode(String str) {
        return new String(Base64.getUrlDecoder().decode(str), StandardCharsets.UTF_8);
    }
}
