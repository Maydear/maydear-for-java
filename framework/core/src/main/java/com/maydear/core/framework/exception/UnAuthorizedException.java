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
 * 需要授权访问资源的异常
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public final class UnAuthorizedException extends StatusCodeException {

    private static final long serialVersionUID = -3760725910304001295L;

    /**
     * 构造一个需要授权访问资源的异常
     */
    public UnAuthorizedException() {
        super(StandardStatusCode.UNAUTHORIZED.getCode(), StandardStatusCode.UNAUTHORIZED.getMessage());
    }

    /**
     * 构造一个需要授权访问资源的异常
     *
     * @param cause 抛出的异常
     */
    public UnAuthorizedException(Throwable cause) {
        super(StandardStatusCode.UNAUTHORIZED.getCode(), StandardStatusCode.UNAUTHORIZED.getMessage(), cause);
    }
}
