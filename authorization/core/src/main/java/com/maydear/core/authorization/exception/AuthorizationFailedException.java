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
package com.maydear.core.authorization.exception;


import com.maydear.core.framework.exception.StatusCodeException;

/**
 * (3401)授权失败
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class AuthorizationFailedException extends StatusCodeException {

    /**
     * 授权失败常量错误码
     */
    private static final int AUTHORIZATION_FAILED_CODE = 3401;

    /**
     * 授权失败常量错误信息
     */
    private static final String AUTHORIZATION_FAILED_MESSAGE = "(2601)授权失败。";

    /**
     * 授权失败异常构造函数
     */
    public AuthorizationFailedException() {
        super(AUTHORIZATION_FAILED_CODE, AUTHORIZATION_FAILED_MESSAGE);
    }

    /**
     * 授权失败异常构造函数
     */
    public AuthorizationFailedException(String message) {
        super(AUTHORIZATION_FAILED_CODE, message);
    }

    /**
     * 授权失败异常构造函数
     *
     * @param cause 抛出的异常
     */
    public AuthorizationFailedException(Throwable cause) {
        super(AUTHORIZATION_FAILED_CODE, AUTHORIZATION_FAILED_MESSAGE, cause);
    }
}
