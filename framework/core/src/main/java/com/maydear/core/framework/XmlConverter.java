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
package com.maydear.core.framework;

import java.io.InputStream;

/**
 * Xml转换接口
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public interface XmlConverter {

    /**
     * 序列化为xml格式的字符串
     * @param object 待序列化的对象
     * @return 返回xml格式的字符串
     */
    String toXml(Object object);

    /**
     * 从xml格式的字符串反序列化
     * @param content 待反序列化xml格式的字符串
     * @param clazz 类型
     * @param <T> 对象类型
     * @return 返回对象
     */
    <T> T fromXml(String content, Class<T> clazz);

    /**
     * 从输入流反序列化对象
     * @param stream 待序列化的输入流
     * @param clazz 类型
     * @param <T> 对象类型
     * @return 返回对象
     */
    <T> T fromXml(InputStream stream, Class<T> clazz);
}
