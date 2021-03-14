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
package com.maydear.core.authorization.annotation;

import java.lang.annotation.*;

/**
 * 授权的注解
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorize {

    /**
     * 策略
     *
     * @return
     */
    String policy() default "";

    /**
     * 角色集合
     *
     * @return 返回角色集合
     */
    String[] roles() default {""};

    /**
     * 认证架构
     *
     * @return
     */
    String authenticationScheme() default "";

    /**
     * 激活认证架构
     *
     * @return
     */
    String activeAuthenticationScheme() default "";
}
