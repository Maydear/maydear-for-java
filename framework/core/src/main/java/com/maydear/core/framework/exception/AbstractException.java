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
 * 抽象基础异常
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public abstract class AbstractException extends Exception {

    private static final long serialVersionUID = 3091504518766271230L;

    /**
     * 构造抽象基础异常，主要用于拦截错误统一处理
     */
    public AbstractException() {
        super();
    }

    /**
     * 构造抽象基础异常，主要用于拦截错误统一处理
     * @param message 异常信息
     */
    public AbstractException(String message) {
        super(message);
    }

    /**
     * 构造抽象基础异常，主要用于拦截错误统一处理
     * @param message 异常信息
     * @param cause 抛出的异常
     */
    public AbstractException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造抽象基础异常，主要用于拦截错误统一处理
     * @param cause 抛出的异常
     */
    public AbstractException(Throwable cause) {
        super(cause);
    }
}
