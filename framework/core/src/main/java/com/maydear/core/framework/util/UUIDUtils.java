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

import org.apache.commons.lang3.RegExUtils;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author phil
 */
public class UUIDUtils {

    /**
     * 静态工具类不应该被实例化
     */
    private UUIDUtils() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * 生成不带中横杆的大写UUID字符串
     *
     * @return 不带中横杆的UUID字符串
     */
    public static String generateNoUnderlineUpperCase() {
        return generateNoUnderline().toUpperCase();
    }

    /**
     * 生成大写的UUID字符串
     *
     * @return 大写的UUID字符串
     */
    public static String generateUpperCase() {
        return generate().toUpperCase();
    }

    /**
     * 生成不带中横杆的UUID字符串
     *
     * @return 不带中横杆的UUID字符串
     */
    public static String generateNoUnderline() {
        return RegExUtils.replaceAll(generate(), "-", "");
    }

    /**
     * 生成UUID字符串
     *
     * @return UUID字符串
     */
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
