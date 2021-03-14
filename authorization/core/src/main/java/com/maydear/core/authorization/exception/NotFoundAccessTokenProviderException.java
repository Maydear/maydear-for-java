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
 * （3403）AccessToken Provider未注册的异常
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class NotFoundAccessTokenProviderException extends StatusCodeException {

    /**
     * AccessToken Provider未注册常量
     */
    private static final int NOT_FOUND_ACCESSTOKEN_PROVIDER_CODE = 3403;

    /**
     * AccessToken Provider未注册常量
     */
    private static final String NOT_FOUND_ACCESSTOKEN_PROVIDER_MESSAGE = "AccessToken Provider 未注册。";

    /**
     * AccessToken Provider未注册异常构造函数
     */
    public NotFoundAccessTokenProviderException() {
        super(NOT_FOUND_ACCESSTOKEN_PROVIDER_CODE, NOT_FOUND_ACCESSTOKEN_PROVIDER_MESSAGE);
    }

    /**
     * AccessToken Provider未注册异常构造函数
     * @param cause 抛出的异常
     */
    public NotFoundAccessTokenProviderException(Throwable cause) {
        super(NOT_FOUND_ACCESSTOKEN_PROVIDER_CODE, NOT_FOUND_ACCESSTOKEN_PROVIDER_MESSAGE, cause);
    }
}
