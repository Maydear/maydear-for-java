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
package com.maydear.core.authorization;

/**
 * 认证架构
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class AuthenticationScheme {

    /**
     * 证书认证
     */
    public static final String CERTIFICATE_SCHEME_NAME = "certificate";

    /**
     * 自定义令牌
     */
    public static final String CUSTOMIZE_SCHEME_NAME = "maydear";


    /**
     * jwt令牌
     */
    public static final String JWT_SCHEME_NAME = "Bearer";
}
