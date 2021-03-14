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
package com.maydear.core.framework.jackson.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.maydear.core.framework.jackson.module.CharSequenceModule;
import com.maydear.core.framework.jackson.module.DateModule;
import com.maydear.core.framework.jackson.module.DefaultSimpleModule;
import com.maydear.core.framework.jackson.module.NumberModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

/**
 * 基于Jackson XmlMapper的重新封装的Xml转换工具类
 *
 * @author mechanic
 * @version 0.0.1
 */
@Slf4j
public class JsonMapper {

    public static final JsonMapper INSTANCE = new JsonMapper();

    private static final String STREAM_TO_JSON_ERROR_MESSAGE = "将Stream形式的json转换成对象时发生错误:";
    private static final String JSON_TO_OBJECT_ERROR_MESSAGE = "将json转换成对象时发生错误:";
    private static final String OBJECT_TO_JSON_ERROR_MESSAGE = "将对象转换成json时发生错误:";
    private ObjectMapper mapper;

    public JsonMapper() {
        this(null);
    }

    public JsonMapper(JsonInclude.Include include) {
        this.mapper = new ObjectMapper();
        if (include != null) {
            this.mapper.setSerializationInclusion(include);
        }
        this.mapper
            .setLocale(Locale.CHINA)
            .configure(SerializationFeature.INDENT_OUTPUT, false)
            .configure(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS, true)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new DefaultSimpleModule())
            .registerModule(new NumberModule())
            .registerModule(new DateModule())
            .registerModule(new CharSequenceModule())
            .getSerializerProvider()
        ;
    }

    public JsonMapper configure(SerializationFeature serializationFeature, boolean state) {
        this.mapper.configure(serializationFeature, state);
        return this;
    }

    /**
     * 将bean转成json
     *
     * @param object 需要转换的对象
     * @return 转换后的json
     */
    public String toJson(Object object) {
        try {
            return this.mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(OBJECT_TO_JSON_ERROR_MESSAGE + object, e);
            return null;
        }
    }

    /**
     * 反序列化json到简单的对象
     *
     * @param content 字符串形式的json
     * @param clazz   输出的对象类型
     * @param <T>     返回的对象类型
     * @return 反序列化后的对象
     */
    public <T> T fromJson(String content, Class<T> clazz) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        try {
            return this.mapper.readValue(content, clazz);
        } catch (IOException e) {
            log.error(JSON_TO_OBJECT_ERROR_MESSAGE + clazz, e);
            return null;
        }
    }

    /**
     * 反序列化json到复杂的泛型对象
     *
     * @param content  字符串形式的json
     * @param javaType 输出的对象类型。
     *                 通过 {@link #buildType(Class, Class)} 或者 {@link #buildType(Class, Class, Class)} 构造
     * @param <T>      返回的对象类型
     * @return 反序列化后的对象
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String content, JavaType javaType) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        try {
            return this.mapper.readValue(content, javaType);
        } catch (IOException e) {
            log.error("" + content, e);
            return null;
        }
    }

    /**
     * 反序列化流形式的json到简单的对象
     *
     * @param stream 流形式的json
     * @param clazz  输出的对象类型
     * @param <T>    返回的对象类型
     * @return 反序列化后的对象
     */
    public <T> T fromJson(InputStream stream, Class<T> clazz) {
        if (stream == null) {
            return null;
        }
        try {
            return this.mapper.readValue(stream, clazz);
        } catch (IOException e) {
            log.error(STREAM_TO_JSON_ERROR_MESSAGE + clazz, e);
            return null;
        }
    }

    /**
     * 反序列化流形式的json到复杂的泛型对象
     *
     * @param stream   流形式的json
     * @param javaType 输出的对象类型。
     *                 通过 {@link #buildType(Class, Class)} 或者 {@link #buildType(Class, Class, Class)} 构造
     * @param <T>      返回的对象类型
     * @return 反序列化后的对象
     */
    public <T> T fromJson(InputStream stream, JavaType javaType) {
        if (stream == null) {
            return null;
        }
        try {
            return this.mapper.readValue(stream, javaType);
        } catch (IOException e) {
            log.error(STREAM_TO_JSON_ERROR_MESSAGE + javaType, e);
            return null;
        }
    }

    /**
     * 反序列化流形式的json到复杂的泛型对象
     *
     * @param content       流形式的json
     * @param typeReference 输出的对象类型
     * @param <T>           返回的对象类型
     * @return 反序列化后的对象
     */
    public <T> T fromJson(String content, TypeReference typeReference) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        try {
            return this.mapper.readValue(content, typeReference);
        } catch (IOException e) {
            log.error(JSON_TO_OBJECT_ERROR_MESSAGE + typeReference, e);
            return null;
        }
    }

    /**
     * 反序列化流形式的json到复杂的泛型对象
     *
     * @param stream        流形式的json
     * @param typeReference 输出的对象类型
     * @param <T>           返回的对象类型
     * @return 反序列化后的对象
     */
    public <T> T fromJson(InputStream stream, TypeReference typeReference) {
        if (stream == null) {
            return null;
        }
        try {
            return this.mapper.readValue(stream, typeReference);
        } catch (IOException e) {
            log.error(STREAM_TO_JSON_ERROR_MESSAGE + typeReference, e);
            return null;
        }
    }


    /**
     * 复制复杂的泛型对象，同时将下划线命名的字段转换为驼峰命名
     *
     * @param source   下划线命名的对象
     * @param javaType 返回的对象
     * @param <T>      返回的对象类型
     * @return 转换后的对象
     */
    public <T> T toCamelCase(Object source, JavaType javaType) {
        if (source == null) {
            return null;
        }
        String content = this.toJson(source);
        return this.fromJson(content, javaType);
    }

    /**
     * 复制bean属性，同时将下划线命名的字段转换为驼峰命名
     *
     * @param source 下划线命名的对象
     * @param clazz  返回的对象
     * @param <T>    返回的对象类型
     * @return 转换后的对象
     */
    public <T> T toCamelCase(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        String content = this.toJson(source);
        return this.fromJson(content, clazz);
    }

    /**
     * 复制复杂的泛型对象，同时将下划线命名的字段转换为驼峰命名
     *
     * @param source        下划线命名的对象
     * @param typeReference 返回的对象
     * @param <T>           返回的对象类型
     * @return 转换后的对象
     */
    public <T> T toCamelCase(Object source, TypeReference typeReference) {
        if (source == null) {
            return null;
        }
        String content = this.toJson(source);
        return this.fromJson(content, typeReference);
    }

    /**
     * 构造带泛型的集合类型
     *
     * @param collectionClass 继承Collection接口的对象。如Map，HashMap，LinkedHashMap等。
     * @param elementClass    积累的泛型对象
     * @return 集合形式的JavaType
     */
    public JavaType buildType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return this.mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    /**
     * 构造Map类型
     *
     * @param mapClass   继承Map接口的对象。如Map，HashMap，LinkedHashMap等。
     * @param keyClass   Map键的类型对象
     * @param valueClass Map值的类型对象
     * @return Map形式的JavaType
     */
    public JavaType buildType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return this.mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    /**
     * 获取原始的ObjectMapper
     *
     * @return 原始的ObjectMapper
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

}
