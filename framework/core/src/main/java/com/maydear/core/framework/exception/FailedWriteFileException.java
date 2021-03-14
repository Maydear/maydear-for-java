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
 * 写入文件失败时抛出的异常
 *
 * @author phil
 */
public class FailedWriteFileException extends StatusCodeException {

    /**
     * 构造无参数异常
     */
    public FailedWriteFileException() {
        super(StandardStatusCode.FAILED_WRITE_FILE.getCode(), StandardStatusCode.FAILED_WRITE_FILE.getMessage());
    }

    /**
     * 构造带异常信息的异常
     *
     * @param cause 异常信息
     */
    public FailedWriteFileException(Throwable cause) {
        super(StandardStatusCode.FAILED_WRITE_FILE.getCode(), StandardStatusCode.FAILED_WRITE_FILE.getMessage(), cause);
    }
}
