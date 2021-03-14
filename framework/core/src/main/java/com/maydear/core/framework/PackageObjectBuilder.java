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
package com.maydear.core.framework;

import com.maydear.core.framework.exception.StatusCodeException;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 包裹对象构造器
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class PackageObjectBuilder {

    /**
     * 静态工具类不应该被实例化
     */
    private PackageObjectBuilder() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param exception 带状态码的异常
     * @param requestId 请求标记编号
     * @return 返回包裹对象
     */
    public static PackageObject getPackageObjectException(StatusCodeException exception, @Nullable UUID requestId) {
        PackageObject packageObject = new PackageObject();
        packageObject.setNow(LocalDateTime.now());
        if (requestId == null) {
            packageObject.setRequestId(UUID.randomUUID());
        } else {
            packageObject.setRequestId(requestId);
        }
        packageObject.setNotification(exception.getMessage());
        packageObject.setBody(null);
        packageObject.setStatusCode(exception.getStatusCode());
        return packageObject;
    }

    /**
     * 通过有效实体获取包裹对象
     *
     * @param body      数据实体
     * @param requestId 请求标记编号
     * @return 返回包裹对象
     */
    public static PackageObject getPackageObject(Object body, @Nullable UUID requestId) {
        PackageObject packageObject = new PackageObject();
        packageObject.setNow(LocalDateTime.now());
        if (requestId == null) {
            packageObject.setRequestId(UUID.randomUUID());
        } else {
            packageObject.setRequestId(requestId);
        }

        if (body != null) {
            packageObject.setNotification(null);
            packageObject.setBody(body);
            packageObject.setStatusCode(StandardStatusCode.OK.getCode());
        } else {
            packageObject.setNotification(StandardStatusCode.NOT_FOUND.getMessage());
            packageObject.setStatusCode(StandardStatusCode.NOT_FOUND.getCode());
        }
        return packageObject;
    }

}
