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
 * (3405)授权验证失败的异常
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class VerificationFailedException extends StatusCodeException {

    /**
     * 授权验证失败状态码常量
     */
    private static final int VERIFICATION_FAILED_CODE = 3405;

    /**
     * 授权验证失败错误消息常量
     */
    private static final String VERIFICATION_FAILED_MESSAGE = "授权验证失败。";

    /**
     * AuthorizationService未找到可用的实现异常构造函数
     */
    public VerificationFailedException() {
        super(VERIFICATION_FAILED_CODE, VERIFICATION_FAILED_MESSAGE);
    }

    /**
     * AuthorizationService未找到可用的实现异常构造函数
     *
     * @param cause 抛出的异常
     */
    public VerificationFailedException(Throwable cause) {
        super(VERIFICATION_FAILED_CODE, VERIFICATION_FAILED_MESSAGE, cause);
    }
}
