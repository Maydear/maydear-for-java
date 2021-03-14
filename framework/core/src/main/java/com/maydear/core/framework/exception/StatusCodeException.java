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

import lombok.Getter;

/**
 * 含有状态码异常类型
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class StatusCodeException extends RuntimeException{

    private static final long serialVersionUID = 2427968364693972232L;

    /**
     * 状态码
     */
    @Getter
    private final int statusCode;

    /**
     * 构造一个含有状态码的异常
     * @param statusCode 状态码
     * @param message 对应状态码的消息
     */
    public StatusCodeException(int statusCode,String message) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * 构造一个含有状态码的异常
     * @param statusCode 状态码
     * @param message 对应状态码的消息
     * @param cause 异常
     */
    public StatusCodeException(int statusCode,String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }
}
