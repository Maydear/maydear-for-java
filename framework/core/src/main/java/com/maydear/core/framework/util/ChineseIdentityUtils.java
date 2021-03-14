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

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证助手类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class ChineseIdentityUtils {

    /**
     * 静态工具类不应该被实例化
     */
    private ChineseIdentityUtils() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * 男女标志因子
     */
    private static final  int GANDER_FACTOR = 2;

    /**
     * 18位的长度
     */
    private static final  int IDENTITY_LENGTH = 18;

    /**
     * 省份标志的长度
     */
    private static final  int IDENTITY_PROVINCE_LENGTH = 2;
    /**
     * 数字有效长度
     */
    private static final  int IDENTITY_EFFECTIVE_POSITION = 17;

    /**
     * 出生日期开始位置
     */
    private static final  int BIRTHDAY_START_POSITION = 6;

    /**
     * 出生日期的结束位置
     */
    private static final  int BIRTHDAY_END_POSITION = 14;
    /**
     * 生日格式
     */
    private static final  String BIRTHDAY_PATTREN = "yyyyMMdd";
    /**
     * 模因子
     */
    private static final  int MOD_FACTOR = 11;
    /**
     * 格式正则校验
     */
    private static final  Pattern CHINESE_IDENTITY_PATTERN = Pattern.compile("^\\d{17}(\\d|x)$");

    /**
     * 城市集
     */
    private static final  String[] PROVINCE_SET = new String[]{null, null, null, null, null, null, null, null, null, null, null, "北京", "天津", "河北", "山西", "内蒙古", null, null, null, null, null, "辽宁", "吉林", "黑龙江", null, null, null, null, null, null, null, "上海", "江苏", "浙江", "安微", "福建", "江西", "山东", null, null, null, "河南", "湖北", "湖南", "广东", "广西", "海南", null, null, null, "重庆", "四川", "贵州", "云南", "西藏", null, null, null, null, null, null, "陕西", "甘肃", "青海", "宁夏", "新疆", null, null, null, null, null, "台湾", null, null, null, null, null, null, null, null, null, "香港", "澳门", null, null, null, null, null, null, null, null, "国外"};

    /**
     * 参数错误的消息
     */
    private static final  String ARGUMENT_EXCEPTION_MESSAGE = "不是合法的中国大陆身份证号码";

    /**
     * 返回身份证号中的性别。
     * <pre>
     *     根据身份证号倒数第二位来判断，1为男, 2为女。
     * </pre>
     *
     * @param identityNo 身份证号
     * @return 性别 (1: 男, 2: 女)
     */
    public static Integer getGander(String identityNo) {
        if (!isValid(identityNo)) {
            throw new IllegalArgumentException(ARGUMENT_EXCEPTION_MESSAGE);
        }
        int gander = Integer.parseInt(StringUtils.substring(identityNo, identityNo.length() - 2, identityNo.length() - 1));

        if (gander % GANDER_FACTOR == 0) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * 获取所属省分
     *
     * @param identityNo 身份证号
     * @return 返回对应的省份，如果返回为null则该身份证号码存在异常
     */
    public static String getProvince(String identityNo) {
        if (!isValid(identityNo)) {
            throw new IllegalArgumentException(ARGUMENT_EXCEPTION_MESSAGE);
        }
        return PROVINCE_SET[getProvinceIndex(identityNo)];
    }

    /**
     * 获取身份证中的出生日期
     *
     * @param identityNo 身份证号
     * @return 返回出生日期
     */
    public static Date getBirthday(String identityNo) {
        if (!isValid(identityNo)) {
            throw new IllegalArgumentException(ARGUMENT_EXCEPTION_MESSAGE);
        }

        String birthdayString = identityNo.substring(BIRTHDAY_START_POSITION, BIRTHDAY_END_POSITION);
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(birthdayString, BIRTHDAY_PATTREN);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取省份编号
     *
     * @param identityNo 身份证号
     * @return 省份编号
     */
    private static int getProvinceIndex(String identityNo) {
        return Integer.parseInt(StringUtils.left(identityNo, IDENTITY_PROVINCE_LENGTH));
    }


    /**
     * 校验是否合法中国身份证号（18位）
     *
     * @param identityNo 待验证的中国身份证号
     * @return 返回一个Boolean类型，验证通过合法则返回true，反之则为false
     */
    public static boolean isValid(String identityNo) {

        //不满18位
        if (identityNo.length() != IDENTITY_LENGTH) {
            return false;
        }

        identityNo = StringUtils.lowerCase(identityNo.replace('Ｘ', 'x').replace('ｘ', 'x'));

        Matcher matcher = CHINESE_IDENTITY_PATTERN.matcher(identityNo);
        if (!matcher.matches()) {
            return false;
        }

        if (PROVINCE_SET[getProvinceIndex(identityNo)] == null) {
            return false;
        }
        String birthdayString = identityNo.substring(BIRTHDAY_START_POSITION, BIRTHDAY_END_POSITION);
        if (!DateExtensionUtils.isValidDate(birthdayString, BIRTHDAY_PATTREN)) {
            return false;
        }

        int[] wi = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
        char[] checkCode = new char[]{'1', '0', 'x', '9', '8', '7', '6', '5', '4', '3', '2'};

        int sum = 0;

        //只取前17位
        for (int i = 0; i < identityNo.length() - 1; i++) {
            sum += Integer.parseInt(String.valueOf(identityNo.charAt(i))) * wi[i];
        }

        int mod = sum % MOD_FACTOR;

        return identityNo.charAt(IDENTITY_EFFECTIVE_POSITION) == checkCode[mod];
    }
}
