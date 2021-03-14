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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式校验工具类
 *
 * @author joshua.jiang
 * @date 2020/3/18
 */
public class StringPatternValidUtils {

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[0-9])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+";

    /**
     * 正则表达式：验证url网址
     */
    public static final String REGEX_URL = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

    /**
     * 手机号验证
     *
     * @param mobile 手机号
     * @return 验证通过返回true
     */
    public static boolean validMobile(final String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGEX_MOBILE);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    /**
     * 邮箱验证
     *
     * @param email 邮箱
     * @return 验证通过返回true
     */
    public static boolean validEmail(final String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * url网址验证
     *
     * @param url 网址
     * @return 验证通过返回true
     */
    public static boolean validUrl(final String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGEX_URL);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    /**
     * 汉字验证
     *
     * @param chinese 中文
     * @return 含中文返回true
     */
    public static boolean validChinese(final String chinese) {
        if (StringUtils.isBlank(chinese)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGEX_CHINESE);
        char[] c = chinese.toCharArray();
        for (char value : c) {
            Matcher matcher = pattern.matcher(String.valueOf(value));
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * ip地址验证
     *
     * @param ipAddress ip地址
     * @return 验证通过返回true
     */
    public static boolean validIpAddress(final String ipAddress) {
        if (StringUtils.isBlank(ipAddress)) {
            return false;
        }
        Pattern patten = Pattern.compile(REGEX_IP_ADDR);
        Matcher matcher = patten.matcher(ipAddress);
        return matcher.matches();
    }
}
