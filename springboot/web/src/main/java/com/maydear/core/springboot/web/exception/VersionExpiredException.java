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
package com.maydear.core.springboot.web.exception;

import com.maydear.core.framework.exception.StatusCodeException;

import java.text.MessageFormat;

/**
 * (3001) Api版本过期异常
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public final class VersionExpiredException  extends StatusCodeException {

    /**
     * Api版本过期异常码常量
     */
    private static final int VERSION_EXPIRED_CODE = 3001;

    /**
     * Api版本过期默认异常信息常量
     */
    private static final String VERSION_EXPIRED_MESSAGE = "当前Api已废弃，请升级";

    /**
     * Api版本过期默认异常信息
     */
    private static final String VERSION_EXPIRED_MESSAGE_PATTERN = "当前Api已废弃，请升级新的Api:[{0}]";

    /**
     * 默认Api版本过期异常构造函数
     */
    public VersionExpiredException() {
        super(VERSION_EXPIRED_CODE, VERSION_EXPIRED_MESSAGE);
    }

    /**
     * 带新版本地址的Api版本过期异常构造函数
     * @param message 新版本的地址
     */
    public VersionExpiredException(String message) {
        super(VERSION_EXPIRED_CODE, MessageFormat.format(VERSION_EXPIRED_MESSAGE_PATTERN,message));
    }

    /**
     * Api版本过期异常构造函数
     * @param message 异常通知信息
     * @param cause 抛出的异常
     */
    public VersionExpiredException(String message, Throwable cause) {
        super(VERSION_EXPIRED_CODE, MessageFormat.format(VERSION_EXPIRED_MESSAGE_PATTERN,message), cause);
    }
}
