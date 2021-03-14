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

/**
 * 抽象的运行类父异常
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public abstract class AbstractRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -7707371684746362335L;

    /**
     * 构造抽象的运行异常，主要用于拦截错误统一处理
     */
    public AbstractRuntimeException() {
        super();
    }

    /**
     * 构造抽象的运行异常，主要用于拦截错误统一处理
     * @param message 异常信息
     */
    public AbstractRuntimeException(String message) {
        super(message);
    }

    /**
     * 构造抽象的运行异常，主要用于拦截错误统一处理
     * @param message 异常信息
     * @param cause 抛出的异常
     */
    public AbstractRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造抽象的运行异常，主要用于拦截错误统一处理
     * @param cause 抛出的异常
     */
    public AbstractRuntimeException(Throwable cause) {
        super(cause);
    }
}