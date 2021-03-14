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
 * 无法处理的实体的异常
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public final class UnprocessableEntityException extends StatusCodeException {

    /**
     * 构造一个无法处理的实体错误的异常
     */
    public UnprocessableEntityException() {
        super(StandardStatusCode.UNPROCESSABLE_ENTITY.getCode(), StandardStatusCode.UNPROCESSABLE_ENTITY.getMessage());
    }

    /**
     * 构造一个无法处理的实体错误的异常
     *
     * @param fieldErrorsJson 错误的参数名
     */
    public UnprocessableEntityException(String fieldErrorsJson) {
        super(StandardStatusCode.UNPROCESSABLE_ENTITY.getCode(), fieldErrorsJson);
    }

    /**
     * 构造一个无法处理的实体错误的异常
     *
     * @param cause 抛出的异常
     */
    public UnprocessableEntityException(Throwable cause) {
        super(StandardStatusCode.UNPROCESSABLE_ENTITY.getCode(), StandardStatusCode.UNPROCESSABLE_ENTITY.getMessage(), cause);
    }
}

