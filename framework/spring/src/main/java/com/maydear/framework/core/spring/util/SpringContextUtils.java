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
package com.maydear.framework.core.spring.util;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * Spring应用程序配置上下文工具类
 *
 * @author phil
 * @version 1.0.0
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull final ApplicationContext applicationContext) {
        SpringContextUtils.initApplicationContext(applicationContext);
    }

    /**
     * 保证静态变量线程安全
     * @param context 上下文
     */
    private static synchronized void initApplicationContext(final ApplicationContext context) {
        applicationContext = context;
    }

    /**
     * 获取Spring应用程序配置上下文
     *
     * @return Spring应用程序配置上下文
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取对象
     *
     * @param name 对象的注册名称
     * @param <T>  对象类型
     * @return Object 一个以所给名字注册的bean的实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     *
     * @param clz 类
     * @param <T> 对象类型
     * @return 类型
     */
    public static <T> Collection<T> getBeans(Class<T> clz) {
        Map<String, T> beanMaps = applicationContext.getBeansOfType(clz);

        return beanMaps.values();
    }


    /**
     * 获取类型为requiredType的对象
     *
     * @param clz 类
     * @param <T> 对象类型
     * @return 类型
     */
    public static <T> T getBean(Class<T> clz) {
        return applicationContext.getBean(clz);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name 注册对象的名称
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    /**
     * 判断注册对象是否单例
     *
     * @param name 注册对象的名称
     * @return boolean 是否单例
     */
    public static boolean isSingleton(String name) {
        return applicationContext.isSingleton(name);
    }

    /**
     * @param name 注册对象的名称
     * @return 注册对象的类型
     */
    public static Class<?> getType(String name) {
        return applicationContext.getType(name);
    }

    /**
     * @param name 注册对象的名称
     * @return 注册对象的别名
     */
    public static String[] getAliases(String name) {
        return applicationContext.getAliases(name);
    }

    /**
     * 获取当前环境的Profile
     *
     * @return 当前环境的Profile
     */
    public static String getActiveProfile() {
        String activeProfile = "";
        if (ArrayUtils.isNotEmpty(applicationContext.getEnvironment().getActiveProfiles())) {
            activeProfile = applicationContext.getEnvironment().getActiveProfiles()[0];
        }
        return activeProfile;
    }

    /**
     * 获取ApplicationName
     *
     * @return 应用名称
     */
    public static String getApplicationName() {
        return applicationContext.getApplicationName();
    }
}
