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
 * JSON转换接口
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public interface JsonConverter {

    /**
     * 序列化为json格式字符串
     * @param object 待序列化的对象
     * @return 返回json格式字符串
     */
    String toJson(Object object);


    /**
     * 反序列化json格式字符串为对象
     * @param content json格式字符串内容
     * @param clazz 对象类型
     * @param <T> 对象泛型
     * @return 返回指定的对象
     */
    <T> T fromJson(String content, Class<T> clazz);

    /**
     * 从输入流反序列化对象
     * @param stream 输入流
     * @param clazz 对象类型
     * @param <T> 对象
     * @return 返回指定的对象
     */
    <T> T fromJson(InputStream stream, Class<T> clazz);
}
