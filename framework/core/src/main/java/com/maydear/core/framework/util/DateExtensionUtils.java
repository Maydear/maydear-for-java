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

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class DateExtensionUtils {
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();
    /**
     * 静态工具类不应该被实例化
     */
    private DateExtensionUtils() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * java.util.Date 转 java.time.LocalDateTime
     *
     * @param date java.util.Date
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(final Date date) {
        return date.toInstant().atZone(ZONE_ID).toLocalDateTime();
    }

    /**
     * 验证字符是否日期格式
     *
     * @param strDate 待验证的日期字符串
     * @param pattern 日期的格式
     * @return 如果格式符合则返回true，反之则为false
     */
    public static boolean isValidDate(String strDate, String pattern) {
        try {
            org.apache.commons.lang3.time.DateUtils.parseDate(strDate, pattern);
        } catch (ParseException e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }

        return true;
    }
}
