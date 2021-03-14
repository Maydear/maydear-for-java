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
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.maydear.core.framework.jackson.module.DefaultSimpleModule;
import com.maydear.core.framework.jackson.serializer.NullToEmptySerializer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;

/**
 * 基于Jackson XmlMapper的重新封装的Xml转换工具类
 *
 * @author mechanic
 * @version 0.0.1
 */
public class XmlMapper {

    private static final Logger logger = LoggerFactory.getLogger(XmlMapper.class);

    private static final String STREAM_TO_XML_ERROR_MESSAGE = "将Stream形式的Xml转换成对象时发生错误:";
    private static final String XML_TO_OBJECT_ERROR_MESSAGE = "将Xml转换成对象时发生错误:";
    private static final String OBJECT_TO_XML_ERROR_MESSAGE = "将对象转换成Xml时发生错误:";
    public static final XmlMapper INSTANCE = new XmlMapper();

    private ObjectMapper mapper;

    public XmlMapper() {
        this(null);
    }

    public XmlMapper(JsonInclude.Include include) {
        this.mapper = new com.fasterxml.jackson.dataformat.xml.XmlMapper()
            .configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        if (include != null) {
            this.mapper.setSerializationInclusion(include);
        }
        this.mapper
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
            .registerModule(new DefaultSimpleModule())
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .getSerializerProvider()
            .setNullValueSerializer(new NullToEmptySerializer());
    }

    public XmlMapper configure(SerializationFeature serializationFeature, boolean state) {
        this.mapper.configure(serializationFeature, state);
        return this;
    }
    /**
     * 将bean转成xml
     *
     * @param object 需要转换的对象
     * @return 转换后的字符串形式的xml
     */
    public String toXml(Object object) {
        try {
            return this.mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.warn(OBJECT_TO_XML_ERROR_MESSAGE + object, e);
            return null;
        }
    }

    /**
     * 反序列化字符串形式的XML到简单的对象
     *
     * @param content 字符串形式的xml
     * @param clazz   输出的对象类型
     * @param <T>     返回的对象类型
     * @return 反序列化后的对象
     */
    public <T> T fromXml(String content, Class<T> clazz) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        try {
            return this.mapper.readValue(content, clazz);
        } catch (IOException e) {
            logger.warn(XML_TO_OBJECT_ERROR_MESSAGE + clazz, e);
            return null;
        }
    }

    /**
     * 反序列化字符串形式的XML到复杂的泛型对象
     *
     * @param content  字符串形式的xml
     * @param javaType 输出的对象类型
     *                 通过 {@link #buildType(Class, Class)} 或者 {@link #buildType(Class, Class, Class)} 构造
     * @param <T>      返回的对象类型
     * @return 反序列化后的对象
     */
    @SuppressWarnings("unchecked")
    public <T> T fromXml(String content, JavaType javaType) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        try {
            return this.mapper.readValue(content, javaType);
        } catch (IOException e) {
            logger.warn(XML_TO_OBJECT_ERROR_MESSAGE + content, e);
            return null;
        }
    }

    /**
     * 反序列化流形式的XML到简单的对象
     *
     * @param stream 流形式的xml
     * @param clazz  输出的对象类型
     * @param <T>    返回的对象类型
     * @return 反序列化后的对象
     */
    public <T> T fromXml(InputStream stream, Class<T> clazz) {
        if (stream == null) {
            return null;
        }
        try {
            return this.mapper.readValue(stream, clazz);
        } catch (IOException e) {
            logger.warn(STREAM_TO_XML_ERROR_MESSAGE + clazz, e);
            return null;
        }
    }

    /**
     * 反序列化流形式的XML到复杂的泛型对象
     *
     * @param stream   流形式的xml
     * @param javaType 输出的对象类型。
     *                 通过 {@link #buildType(Class, Class)} 或者 {@link #buildType(Class, Class, Class)} 构造
     * @param <T>      返回的对象类型
     * @return 反序列化后的对象
     */
    public <T> T fromXml(InputStream stream, JavaType javaType) {
        if (stream == null) {
            return null;
        }
        try {
            return this.mapper.readValue(stream, javaType);
        } catch (IOException e) {
            logger.warn(STREAM_TO_XML_ERROR_MESSAGE + javaType, e);
            return null;
        }
    }

    /**
     * 反序列化流形式的XML到复杂的泛型对象
     *
     * @param content       字符串形式的xml
     * @param typeReference 输出的对象类型。
     *                      通过 {@link #buildType(Class, Class)} 或者 {@link #buildType(Class, Class, Class)} 构造
     * @param <T>           返回的对象类型
     * @return 反序列化后的对象
     */
    public <T> T fromXml(String content, TypeReference typeReference) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        try {
            return this.mapper.readValue(content, typeReference);
        } catch (IOException e) {
            logger.warn(STREAM_TO_XML_ERROR_MESSAGE + typeReference, e);
            return null;
        }
    }

    /**
     * 反序列化流形式的XML到复杂的泛型对象
     *
     * @param stream        流形式的xml
     * @param typeReference 输出的对象类型。
     *                      通过 {@link #buildType(Class, Class)} 或者 {@link #buildType(Class, Class, Class)} 构造
     * @param <T>           返回的对象类型
     * @return 反序列化后的对象
     */
    public <T> T fromXml(InputStream stream, TypeReference typeReference) {
        if (stream == null) {
            return null;
        }
        try {
            return this.mapper.readValue(stream, typeReference);
        } catch (IOException e) {
            logger.warn(STREAM_TO_XML_ERROR_MESSAGE + typeReference, e);
            return null;
        }
    }

    /**
     * 反序列化XML文件到复杂的泛型对象
     *
     * @param path     xml文件路径
     * @param javaType 输出的对象类型
     * @param <T>      返回的对象类型
     * @return 反序列化后的对象
     */
    public <T> T fromResourceXml(String path, JavaType javaType) {
        return this.fromXml(getResourceContent(path), javaType);
    }

    /**
     * 反序列化XML文件到复杂的泛型对象
     *
     * @param path         xml文件路径
     * @param javaType     输出的对象类型
     * @param rootNodeName 开始读取的根节点
     * @param <T>          返回的对象类型
     * @return 反序列化后的对象
     */
    public <T> T fromResourceXml(String path, JavaType javaType, String rootNodeName) {
        return this.fromXml(getResourceContent(path, rootNodeName), javaType);
    }

    /**
     * 反序列化XML文件到简单的对象
     *
     * @param path  xml文件路径
     * @param clazz 输出的对象类型
     * @param <T>   返回的对象类型
     * @return 反序列化后的对象
     */
    public <T> T fromResourceXml(String path, Class<T> clazz) {
        return this.fromXml(getResourceContent(path), clazz);
    }

    /**
     * 反序列化XML文件到简单的对象
     *
     * @param path         xml文件路径
     * @param clazz        输出的对象类型
     * @param rootNodeName 开始读取的根节点
     * @param <T>          返回的对象类型
     * @return 反序列化后的对象
     */
    public <T> T fromResourceXml(String path, Class<T> clazz, String rootNodeName) {
        return this.fromXml(getResourceContent(path, rootNodeName), clazz);
    }

    /**
     * 反序列化流形式的XML到复杂的泛型对象
     *
     * @param path          xml文件路径
     * @param typeReference 输出的对象类型。
     *                      通过 {@link #buildType(Class, Class)} 或者 {@link #buildType(Class, Class, Class)} 构造
     * @param <T>           返回的对象类型
     * @return 反序列化后的对象
     */
    public <T> T fromResourceXml(String path, TypeReference typeReference) {
        return this.fromXml(getResourceContent(path), typeReference);
    }

    /**
     * 反序列化流形式的XML到复杂的泛型对象
     *
     * @param path          xml文件路径
     * @param typeReference 输出的对象类型。
     *                      通过 {@link #buildType(Class, Class)} 或者 {@link #buildType(Class, Class, Class)} 构造
     * @param rootNodeName  开始读取的根节点
     * @param <T>           返回的对象类型
     * @return 反序列化后的对象
     */
    public <T> T fromResourceXml(String path, TypeReference typeReference, String rootNodeName) {
        return this.fromXml(getResourceContent(path, rootNodeName), typeReference);
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
     * 获取原始的XmlMapper
     *
     * @return 原始的XmlMapper
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * 获取资源文件内容
     *
     * @param path 资源文件路径
     * @return 文件内容
     */
    public String getResourceContent(String path) {
        return getResourceContent(path, null);
    }

    /**
     * 获取资源文件内容
     *
     * @param path         资源文件路径
     * @param rootNodeName 更节点名称
     * @return 文件内容
     */
    public String getResourceContent(String path, String rootNodeName) {
        if (StringUtils.isBlank(path)) {
            return null;
        }
        String prefix = "/";
        if (!StringUtils.startsWith(path, prefix)) {
            path = prefix + path;
        }
        String content = null;
        try {
            content = IOUtils.resourceToString(path, StandardCharsets.UTF_8);
            if (!StringUtils.isBlank(rootNodeName)) {
                content = getNode(content, rootNodeName);
            }
        } catch (IOException e) {
            logger.warn("读取XML文件是发生错误。" + e);
        }
        return content;
    }

    /**
     * 获取资源文件内容
     *
     * @param content      xml内容
     * @param rootNodeName 更节点名称
     * @return 文件内容
     */
    public String getNode(String content, String rootNodeName) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        String startRootName = "<" + rootNodeName + ">";
        String endRootName = "</" + rootNodeName + ">";
        content = StringUtils.substringAfter(content, startRootName);
        content = StringUtils.substringBeforeLast(content, endRootName);
        return startRootName + content + endRootName;
    }
}
