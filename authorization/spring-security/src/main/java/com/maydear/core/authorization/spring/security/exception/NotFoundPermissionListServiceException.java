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
package com.maydear.core.authorization.spring.security.exception;

import com.maydear.core.framework.exception.StatusCodeException;

/**
 * 未注入PermissionListService实现的异常
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class NotFoundPermissionListServiceException extends StatusCodeException {

    /**
     * 未注入PermissionListService实现的常量
     */
    private static final int NOT_FOUND_PERMISSION_LIST_SERVICE_CODE = 4003;

    /**
     * 未注入PermissionListService实现的常量
     */
    private static final String NOT_FOUND_PERMISSION_LIST_SERVICE_MESSAGE = "请注入“PermissionListService”接口的实现";

    /**
     *  未注入PermissionListService实现的构造函数
     */
    public NotFoundPermissionListServiceException() {
        super(NOT_FOUND_PERMISSION_LIST_SERVICE_CODE, NOT_FOUND_PERMISSION_LIST_SERVICE_MESSAGE);
    }

    /**
     * 未注入PermissionListService实现的构造函数
     * @param cause 抛出的异常
     */
    public NotFoundPermissionListServiceException(Throwable cause) {
        super(NOT_FOUND_PERMISSION_LIST_SERVICE_CODE, NOT_FOUND_PERMISSION_LIST_SERVICE_MESSAGE, cause);
    }
}
