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
 * （3404）AuthorizationService未找到可用的实现的异常
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class NotFoundAuthorizationServiceException extends StatusCodeException {

    /**
     * AuthorizationService未找到可用的实现常量
     */
    private static final int NOT_FOUND_AUTH_USER_SERVICE_CODE = 3404;

    /**
     * AuthorizationService未找到可用的实现常量
     */
    private static final String NOT_FOUND_AUTH_USER_SERVICE_MESSAGE = "AuthorizationService未找到可用的实现。";

    /**
     * AuthorizationService未找到可用的实现异常构造函数
     */
    public NotFoundAuthorizationServiceException() {
        super(NOT_FOUND_AUTH_USER_SERVICE_CODE, NOT_FOUND_AUTH_USER_SERVICE_MESSAGE);
    }

    /**
     * AuthorizationService未找到可用的实现异常构造函数
     *
     * @param cause 抛出的异常
     */
    public NotFoundAuthorizationServiceException(Throwable cause) {
        super(NOT_FOUND_AUTH_USER_SERVICE_CODE, NOT_FOUND_AUTH_USER_SERVICE_MESSAGE, cause);
    }
}
