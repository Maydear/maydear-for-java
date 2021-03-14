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
package com.maydear.core.framework.spring.web.advice;

import com.google.common.collect.Lists;
import com.maydear.core.framework.PackageObject;
import com.maydear.core.framework.PackageObjectBuilder;
import com.maydear.core.framework.StandardStatusCode;
import com.maydear.core.framework.annotation.NotUsePackageObject;
import com.maydear.core.framework.jackson.mapper.JsonMapper;
import com.maydear.core.framework.spring.web.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * 全局响应处理
 *
 * @author phil
 * @version 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class ResponseHandlerAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(@Nullable MethodParameter returnType, @NonNull Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {
        if (body != null) {
            for (Class c : getExclusions()) {
                if (body.getClass().equals(c)) {
                    return body;
                }
            }
        }

        if (body instanceof PackageObject) {
            return authorizedFilter((PackageObject) body, response);
        } else {
            if (returnType.getMethod().isAnnotationPresent(NotUsePackageObject.class)) {
                return body;
            } else {
                PackageObject packageObject = PackageObjectBuilder.getPackageObject(body, RequestUtils.getRequestedId());
                if (body instanceof String) {
                    return JsonMapper.INSTANCE.toJson(packageObject);
                } else {
                    return authorizedFilter(packageObject, response);
                }
            }
        }
    }

    private Object authorizedFilter(PackageObject packageObject, ServerHttpResponse response) {
        if (packageObject.getStatusCode() == StandardStatusCode.UNAUTHORIZED.getCode()) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
        } else if (packageObject.getStatusCode() == StandardStatusCode.FORBIDDEN.getCode()) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
        }

        return packageObject;
    }


    protected List<Class> getExclusions() {
        return Lists.newArrayList();
    }
}
