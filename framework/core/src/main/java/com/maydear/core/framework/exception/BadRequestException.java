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
package com.maydear.core.framework.exception;

import com.maydear.core.framework.StandardStatusCode;

/**
 * 参数错误异常
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public final class BadRequestException extends StatusCodeException {

    private static final long serialVersionUID = 8491070810106276170L;

    /**
     * 构造一个运行时错误的异常
     */
    public BadRequestException() {
        super(StandardStatusCode.BAD_REQUEST.getCode(), StandardStatusCode.BAD_REQUEST.getMessage());
    }
    /**
     * 构造一个无法处理的实体错误的异常
     *
     * @param message 错误的参数名
     */
    public BadRequestException(String message) {
        super(StandardStatusCode.BAD_REQUEST.getCode(), message);
    }

    /**
     * 构造一个运行时错误的异常
     *
     * @param cause 抛出的异常
     */
    public BadRequestException(Throwable cause) {
        super(StandardStatusCode.BAD_REQUEST.getCode(), StandardStatusCode.BAD_REQUEST.getMessage(), cause);
    }
}
