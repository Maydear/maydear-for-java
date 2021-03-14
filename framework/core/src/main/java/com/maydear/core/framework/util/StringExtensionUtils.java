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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * StringUtils 扩展
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class StringExtensionUtils {

    /**
     * 静态工具类不应该被实例化
     */
    private StringExtensionUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 默认分隔符
     */
    private static final String DEFAULT_SEPARATOR = ",";


    /**
     * 将字符型集合拼接成默认分隔符分隔的字符串
     *
     * @param collection 字符型集合
     * @return 返回根据分隔符拼接后的字符串
     */
    public static String join(final Collection<String> collection) {
        return join(DEFAULT_SEPARATOR, collection);
    }

    /**
     * 将字符型集合拼接成指定分隔符分隔的字符串
     *
     * @param separator  分隔符
     * @param collection 字符型集合
     * @return 返回根据分隔符拼接后的字符串
     */
    public static String join(final String separator, final Collection<String> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String str : collection) {
            sb.append(str).append(separator);
        }
        return StringUtils.substringBeforeLast(sb.toString(), separator);
    }

    /**
     * 将字符型集合拼接成指定分隔符分隔的字符串
     *
     * @param separator         分隔符
     * @param map               字符型键值集合
     * @param keyValueSeparator 键值分隔符
     * @return 返回根据分隔符拼接后的字符串
     */
    public static String join(final String separator, final Map<String, String> map, final String keyValueSeparator) {
        if (MapUtils.isEmpty(map)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey() + keyValueSeparator + entry.getValue()).append(separator);
        }
        return StringUtils.substringBeforeLast(sb.toString(), separator);
    }

    /**
     * 将字符型集合拼接成指定分隔符分隔的字符串
     *
     * @param separator 分隔符
     * @param args      字符型可变参数
     * @return 返回根据分隔符拼接后的字符串
     */
    public static String join(final String separator, final String... args) {

        StringBuilder sb = new StringBuilder();
        for (String str : args) {
            sb.append(str).append(separator);
        }
        return StringUtils.substringBeforeLast(sb.toString(), separator);
    }


    /**
     * 编码字符串，编码为UTF-8
     *
     * @param str 字符串
     * @return 编码后的字节码
     */
    public static byte[] utf8Bytes(CharSequence str) {
        return bytes(str, StandardCharsets.UTF_8);
    }

    /**
     * 编码字符串<br>
     * 使用系统默认编码
     *
     * @param str 字符串
     * @return 编码后的字节码
     */
    public static byte[] bytes(CharSequence str) {
        return bytes(str, Charset.defaultCharset());
    }

    /**
     * 编码字符串
     *
     * @param str     字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 编码后的字节码
     */
    public static byte[] bytes(CharSequence str, String charset) {
        return bytes(str, StringUtils.isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * 编码字符串
     *
     * @param str     字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 编码后的字节码
     */
    public static byte[] bytes(CharSequence str, Charset charset) {
        if (str == null) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }

        if (null == charset) {
            return str.toString().getBytes();
        }
        return str.toString().getBytes(charset);
    }

    /**
     * 将对象转为字符串<br>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组 2、对象数组会调用Arrays.toString方法
     *
     * @param obj 对象
     * @return 字符串
     */
    public static String utf8Str(Object obj) {
        return build(obj, StandardCharsets.UTF_8);
    }

    /**
     * 将对象转为字符串
     *
     * <pre>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj         对象
     * @param charsetName 字符集
     * @return 字符串
     */
    public static String build(Object obj, String charsetName) {
        return build(obj, Charset.forName(charsetName));
    }

    /**
     * 将对象转为字符串
     * <pre>
     * 	 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 	 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj     对象
     * @param charset 字符集
     * @return 字符串
     */
    public static String build(Object obj, Charset charset) {
        if (ObjectUtils.isEmpty(obj)) {
            return StringUtils.EMPTY;
        }

        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return build((byte[]) obj, charset);
        } else if (obj instanceof Byte[]) {
            return build((Byte[]) obj, charset);
        } else if (obj instanceof ByteBuffer) {
            return build((ByteBuffer) obj, charset);
        } else if (obj.getClass().isArray()) {
            return ArrayUtils.toString(obj);
        }

        return obj.toString();
    }

    /**
     * 将byte数组转为字符串
     *
     * @param bytes   byte数组
     * @param charset 字符集
     * @return 字符串
     */
    public static String build(byte[] bytes, String charset) {
        return build(bytes, StringUtils.isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * 解码字节码
     *
     * @param data    字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 解码后的字符串
     */
    public static String build(byte[] data, Charset charset) {
        if (data == null) {
            return null;
        }

        if (null == charset) {
            return new String(data);
        }
        return new String(data, charset);
    }

    /**
     * 将Byte数组转为字符串
     *
     * @param bytes   byte数组
     * @param charset 字符集
     * @return 字符串
     */
    public static String build(Byte[] bytes, String charset) {
        return build(bytes, StringUtils.isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * 解码字节码
     *
     * @param data    字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 解码后的字符串
     */
    public static String build(Byte[] data, Charset charset) {
        if (data == null) {
            return null;
        }

        byte[] bytes = new byte[data.length];
        Byte dataByte;
        for (int i = 0; i < data.length; i++) {
            dataByte = data[i];
            bytes[i] = (null == dataByte) ? -1 : dataByte;
        }

        return build(bytes, charset);
    }

    /**
     * 将编码的byteBuffer数据转换为字符串
     *
     * @param data    数据
     * @param charset 字符集，如果为空使用当前系统字符集
     * @return 字符串
     */
    public static String build(ByteBuffer data, String charset) {
        if (data == null) {
            return null;
        }

        return build(data, Charset.forName(charset));
    }

    /**
     * 将编码的byteBuffer数据转换为字符串
     *
     * @param data    数据
     * @param charset 字符集，如果为空使用当前系统字符集
     * @return 字符串
     */
    public static String build(ByteBuffer data, Charset charset) {
        if (null == charset) {
            charset = Charset.defaultCharset();
        }
        return charset.decode(data).toString();
    }

    /**
     * {@link CharSequence} 转为字符串，null安全
     *
     * @param cs {@link CharSequence}
     * @return 字符串
     */
    public static String build(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }

}
