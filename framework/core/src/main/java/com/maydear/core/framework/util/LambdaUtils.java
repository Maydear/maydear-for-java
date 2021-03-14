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

import com.maydear.core.framework.SerializableFunction;
import com.maydear.core.framework.exception.SerializedLambdaException;

import java.lang.invoke.SerializedLambda;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Lambda 解析工具类
 *
 * @author kelvin.liang
 * @since 2021-01-23
 */
public final class LambdaUtils {

    private static final String METHOD_WRITE_REPLACE = "writeReplace";

    /**
     * SerializedLambdaException 反序列化缓存
     */
    private static final Map<String, WeakReference<SerializedLambda>> FUNC_CACHE = new ConcurrentHashMap<>();

    /**
     * 解析 lambda 表达式
     * 该缓存可能会在任意不定的时间被清除
     *
     * @param func 需要解析的 lambda 对象
     * @return 返回解析后的结果
     */
    public static SerializedLambda resolve(SerializableFunction<?, ?> func) {
        Class<?> clazz = func.getClass();
        String name = clazz.getName();
        return Optional.ofNullable(FUNC_CACHE.get(name))
            .map(WeakReference::get)
            .orElseGet(() -> {
                SerializedLambda lambda = getSerializedLambda(func);
                FUNC_CACHE.put(name, new WeakReference<>(lambda));
                return lambda;
            });
    }

    private static SerializedLambda getSerializedLambda(SerializableFunction<?, ?> fn) {
        //先检查缓存中是否已存在
        SerializedLambda lambda = null;
        try {//提取SerializedLambda并缓存
            Class<?> classZ = fn.getClass();
            if (!classZ.isSynthetic()) {
                throw new SerializedLambdaException(classZ.getName() + ":" + "方法仅能传入 lambda 表达式产生的合成类");
            }
            Method method = classZ.getDeclaredMethod(METHOD_WRITE_REPLACE);
            method.setAccessible(Boolean.TRUE);
            lambda = (SerializedLambda) method.invoke(fn);
        } catch (Exception e) {
            throw new SerializedLambdaException("获取SerializedLambda异常, class=" + fn.getClass().getSimpleName(), e);
        }
        return lambda;
    }

    public static String getPropertyName(SerializableFunction<?, ?> action) {
        SerializedLambda serializedLambda = LambdaUtils.resolve(action);
        String methodName = serializedLambda.getImplMethodName();
        return PropertyNameUtils.methodToProperty(methodName);
    }
}
