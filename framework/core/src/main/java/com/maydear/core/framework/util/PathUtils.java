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

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * 路径工具
 *
 * @author phil
 * @version 1.0.0
 */
public class PathUtils {

    /**
     * 静态工具类不应该被实例化
     */
    private PathUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 根路径净化吃掉末尾分割符
     *
     * @param rootPath 根路径
     * @return 返回不含文件夹分割符的根路径
     */
    private static String rootPathCleanup(String rootPath) {
        String root = rootPath;
        if (StringUtils.endsWith(rootPath, File.separator)) {
            root = StringUtils.removeEnd(rootPath, File.separator);
        }
        return root;
    }

    /**
     * 在跟定的根路径后拼接根据指定年月生成的路径。格式为：根路径/2018/12/02
     *
     * @param rootPath 根路径
     * @return 返回路径字符
     */
    public static String getPathAsDate(String rootPath, LocalDate date) {
        StringJoiner stringJoiner = new StringJoiner(File.separator);
        String root = rootPathCleanup(rootPath);
        if (StringUtils.isNotBlank(root)) {
            stringJoiner.add(root.trim());
        }
        stringJoiner.add(String.valueOf(date.getYear()));
        stringJoiner.add(String.valueOf(date.getMonthValue()));
        stringJoiner.add(String.valueOf(date.getDayOfMonth()));
        return stringJoiner.toString();
    }

    /**
     * 在跟定的根路径后拼接根据当前年月生成的路径。格式为：根路径/2018/12/02
     *
     * @param rootPath 根路径
     * @return String
     */
    public static String getPathAsDate(String rootPath) {
        return getPathAsDate(rootPath, LocalDate.now());
    }

    /**
     * 根据当前日期生成路径。格式为：2018/12/02
     *
     * @return String
     */
    public static String getPathAsDate() {
        return getPathAsDate(null, LocalDate.now());
    }

    /**
     * 在指定的根路径后拼接根据当前日期生成的路径。。格式为：201812/
     *
     * @return String
     */
    public static String getPathAsYearAndMonth(String rootPath) {
        return getPathAsYearAndMonth(rootPath, LocalDate.now());
    }


    /**
     * 在指定的根路径后拼接根据指定日期生成的路径。。格式为：201812/
     *
     * @return String
     */
    public static String getPathAsYearAndMonth(String rootPath, LocalDate date) {
        StringJoiner stringJoiner = new StringJoiner(File.separator);
        String root = rootPathCleanup(rootPath);
        if (StringUtils.isNotBlank(root)) {
            stringJoiner.add(root.trim());
        }
        stringJoiner.add(String.valueOf(date.getYear()) + String.valueOf(date.getMonthValue()));
        return stringJoiner.toString();
    }

    /**
     * 根据当前日期生成路径。格式为：201812/
     *
     * @return String
     */
    public static String getPathAsYearAndMonth() {
        return getPathAsYearAndMonth(null, LocalDate.now());
    }

    /**
     * 在指定的根路径后拼接根据指定年月生成的路径。格式为：根路径/2018/12
     *
     * @param rootPath
     * @param date
     * @return
     */
    public static String getPathAsYearAndMonthHasSeparator(String rootPath, LocalDate date) {
        StringJoiner stringJoiner = new StringJoiner(File.separator);
        String root = rootPath;
        if (StringUtils.endsWith(rootPath, File.separator)) {
            root = StringUtils.removeEnd(rootPath, File.separator);
        }
        if (StringUtils.isNotBlank(root)) {
            stringJoiner.add(root.trim());
        }
        stringJoiner.add(String.valueOf(date.getYear()));
        stringJoiner.add(String.valueOf(date.getMonthValue()));
        return stringJoiner.toString();
    }

    /**
     * 在指定的根路径后拼接根据当前年月生成的路径。格式为：根路径/201812
     *
     * @param rootPath 根路径
     * @return String
     */
    public static String getPathAsYearAndMonthHasSeparator(String rootPath) {
        return getPathAsYearAndMonthHasSeparator(rootPath, LocalDate.now());
    }

    /**
     * 根据当前年月生成路径
     *
     * @return String
     */
    public static String getPathAsYearAndMonthHasSeparator() {
        LocalDate date = LocalDate.now();
        return date.getYear() + File.separator + date.getMonthValue() + File.separator;
    }

    /**
     * 路径拼接
     *
     * @param args 待拼接的字符
     * @return 返回拼接后的文件路径
     */
    public static String combine(String... args) {

        Path path = Paths.get("", args);
        return path.toString();
    }

    /**
     * 路径Url拼接
     *
     * @param args 待拼接的字符
     * @return 返回拼接后的Url路径
     */
    public static String combineUrlPath(String... args) {
        Path path = Paths.get("", args);
        return path.toUri().toString();
    }
}
