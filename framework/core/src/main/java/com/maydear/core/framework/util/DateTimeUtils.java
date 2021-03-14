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

import org.apache.commons.lang3.ObjectUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 日期时间工具类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class DateTimeUtils {

    /**
     * 静态类不应该被实例化
     */
    private DateTimeUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取指定日期的0时0分0秒
     *
     * @param dateTime 待转换的日期时间
     * @return 返回指定日期的0时0分0秒
     */
    public static LocalDateTime getDayStart(LocalDateTime dateTime) {
        if (ObjectUtils.isEmpty(dateTime)) {
            return null;
        }
        return LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 0, 0, 0);
    }

    /**
     * 获取指定日期的0时0分0秒
     *
     * @param date 待转换的日期
     * @return 返回指定日期的0时0分0秒
     */
    public static LocalDateTime getDayStart(LocalDate date) {
        if (ObjectUtils.isEmpty(date)) {
            return null;
        }
        return LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0, 0);
    }

    /**
     * 获取指定日期的23时59分59秒
     *
     * @param dateTime 待转换的日期时间
     * @return 返回指定日期的23时59分59秒
     */
    public static LocalDateTime getDayEnd(LocalDateTime dateTime) {
        if (ObjectUtils.isEmpty(dateTime)) {
            return null;
        }
        return LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 23, 59, 59);
    }

    /**
     * 获取指定日期的23时59分59秒
     *
     * @param date 待转换的日期
     * @return 返回指定日期的23时59分59秒
     */
    public static LocalDateTime getDayEnd(LocalDate date) {
        if (ObjectUtils.isEmpty(date)) {
            return null;
        }
        return LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59, 59);
    }

    /**
     * Unix的微秒数
     *
     * @param dateTime 时间
     * @return 返回Unix的微秒数
     */
    public static Long toUnixTimeMilliseconds(LocalDateTime dateTime) {
        return toUnixTimeMilliseconds(dateTime, ZoneOffset.UTC);
    }

    /**
     * Unix的微秒数
     *
     * @param dateTime 时间
     * @return 返回Unix的微秒数
     */
    public static Long toUnixTimeSeconds(LocalDateTime dateTime) {
        return toUnixTimeSeconds(dateTime, ZoneOffset.UTC);
    }

    /**
     * 从Unix的微秒数构造时间对象
     *
     * @param milliseconds Unix的微秒数
     * @return 时间
     */
    public static LocalDateTime formUnixTimeMilliseconds(long milliseconds) {
        return formUnixTimeMilliseconds(milliseconds, ZoneOffset.UTC);
    }

    /**
     * 从Unix的微秒数构造时间对象
     *
     * @param seconds Unix的秒数
     * @return 时间
     */
    public static LocalDateTime formUnixTimeSeconds(long seconds) {
        return formUnixTimeSeconds(seconds, ZoneOffset.UTC);
    }

    /**
     * Unix的微秒数
     *
     * @param dateTime 时间
     * @return 返回Unix的微秒数
     */
    public static Long toUnixTimeMilliseconds(LocalDateTime dateTime, ZoneOffset zone) {
        return dateTime.toInstant(zone).toEpochMilli();
    }

    /**
     * Unix的微秒数
     *
     * @param dateTime 时间
     * @param zone     时区
     * @return 返回Unix的微秒数
     */
    public static Long toUnixTimeSeconds(LocalDateTime dateTime, ZoneOffset zone) {
        return dateTime.toInstant(zone).getEpochSecond();
    }

    /**
     * 从Unix的微秒数构造时间对象
     *
     * @param milliseconds Unix的微秒数
     * @param zone         时区
     * @return 时间
     */
    public static LocalDateTime formUnixTimeMilliseconds(long milliseconds, ZoneOffset zone) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), zone);
    }

    /**
     * 从Unix的微秒数构造时间对象
     *
     * @param seconds Unix的秒数
     * @param zone    时区
     * @return 时间
     */
    public static LocalDateTime formUnixTimeSeconds(long seconds, ZoneOffset zone) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), zone);
    }
}
