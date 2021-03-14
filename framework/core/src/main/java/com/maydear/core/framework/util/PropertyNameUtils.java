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

import com.maydear.core.framework.exception.ReflectionException;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

/**
 * 属性名称工具类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class PropertyNameUtils {

    private static final String PREFIX_IS = "is";
    private static final String PREFIX_GET = "get";
    private static final String PREFIX_SET = "set";

    private static final int PREFIX_IS_BEGIN_INDEX = 2;
    private static final int PREFIX_GETSET_BEGIN_INDEX = 3;

    private PropertyNameUtils() {
    }

    /**
     * 属性方法名转属性名
     *
     * @param name 方法名称
     * @return 返回属性名
     */
    public static String methodToProperty(final String name) {
        String propertyName = name;
        if (StringUtils.startsWith(name, PREFIX_IS)) {
            propertyName = StringUtils.substring(name, PREFIX_IS_BEGIN_INDEX);
        } else {
            if (!StringUtils.startsWith(name, PREFIX_GET) && !StringUtils.startsWith(name, PREFIX_SET)) {
                throw new ReflectionException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
            }
            propertyName = StringUtils.substring(name, PREFIX_GETSET_BEGIN_INDEX);
        }

        if (propertyName.length() == 1 || propertyName.length() > 1) {
            if (!Character.isUpperCase(propertyName.charAt(1))) {
                propertyName = propertyName.substring(0, 1).toLowerCase(Locale.ENGLISH) + propertyName.substring(1);
            }
        }

        return propertyName;
    }

    /**
     * 是否是属性方法
     *
     * @param name 方法名称
     * @return 如果是属性方法返回true，反之则为false
     */
    public static boolean isProperty(final String name) {
        return name.startsWith(PREFIX_GET) || name.startsWith(PREFIX_SET) || name.startsWith(PREFIX_IS);
    }

    /**
     * 是否Getter方法
     *
     * @param name 方法名称
     * @return 如果Getter方法返回true，反之则为false
     */
    public static boolean isGetter(final String name) {
        return name.startsWith(PREFIX_GET) || name.startsWith(PREFIX_IS);
    }

    /**
     * 是否Setter方法
     *
     * @param name 获取名称
     * @return 如果Setter方法返回true，反之则为false
     */
    public static boolean isSetter(final String name) {
        return name.startsWith(PREFIX_SET);
    }
}
