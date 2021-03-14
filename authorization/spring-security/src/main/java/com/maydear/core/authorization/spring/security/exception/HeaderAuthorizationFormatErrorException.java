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
package com.maydear.core.authorization.spring.security.exception;

import com.maydear.core.framework.exception.StatusCodeException;

/**
 * 请求头Authorization格式错误的异常
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class HeaderAuthorizationFormatErrorException extends StatusCodeException {

    /**
     * 请求头Authorization格式错误常量
     */
    private static final int HEADER_AUTHORIZATION_FORMAT_ERROR_CODE = 4002;

    /**
     * 请求头Authorization格式错误常量
     */
    private static final String HEADER_AUTHORIZATION_FORMAT_ERROR_MESSAGE = "请求头Authorization格式错误，格式为：“Authorization: <type> <credentials>”。";

    /**
     *  请求头Authorization格式错误异常构造函数
     */
    public HeaderAuthorizationFormatErrorException() {
        super(HEADER_AUTHORIZATION_FORMAT_ERROR_CODE, HEADER_AUTHORIZATION_FORMAT_ERROR_MESSAGE);
    }

    /**
     * 请求头Authorization格式错误异常构造函数
     * @param cause 抛出的异常
     */
    public HeaderAuthorizationFormatErrorException(Throwable cause) {
        super(HEADER_AUTHORIZATION_FORMAT_ERROR_CODE, HEADER_AUTHORIZATION_FORMAT_ERROR_MESSAGE, cause);
    }
}
