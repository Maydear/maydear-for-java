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
package com.maydear.core.framework.spring.web.util;

import com.maydear.core.framework.PackageObject;
import com.maydear.core.framework.PackageObjectBuilder;
import com.maydear.core.framework.exception.NotFoundException;
import com.maydear.core.framework.exception.StatusCodeException;
import com.maydear.core.framework.jackson.mapper.JsonMapper;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Response 工具类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public final class ResponseUtils {

    /**
     * 静态工具类不应该被实例化
     */
    private ResponseUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取 HttpServletResponse
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes());
        Assert.notNull(servletRequestAttributes, "request must not be null.");
        return servletRequestAttributes.getResponse();
    }

    /**
     * 直接输出响应消息
     *
     * @param response HttpServletResponse
     * @param body     响应内容
     * @throws IOException 输出信息发生错误时抛出的异常
     */
    public static void writeContent(HttpServletResponse response, String body) throws IOException {
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setCharacterEncoding(CharEncoding.UTF_8);
        response.setStatus(200);
        response.getWriter().write(body);
    }

    /**
     * 直接输出响应消息
     *
     * @param body 响应内容
     * @throws IOException 输出信息发生错误时抛出的异常
     */
    public static void writeContent(String body) throws IOException {
        writeContent(getResponse(), body);
    }

    /**
     * 直接输出包裹格式响应消息
     *
     * @param response HttpServletResponse
     * @param body     响应主题
     * @throws IOException 输出信息发生错误时抛出的异常
     */
    public static void writePackageObject(HttpServletResponse response, Object body) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(CharEncoding.UTF_8);
        response.setStatus(HttpStatus.OK.value());
        PackageObject packageObject;
        if (ObjectUtils.isNotEmpty(body)) {
            if (body instanceof StatusCodeException) {
                packageObject = PackageObjectBuilder.getPackageObjectException((StatusCodeException) body, RequestUtils.getRequestedId());
            } else {
                packageObject = PackageObjectBuilder.getPackageObject(body, RequestUtils.getRequestedId());
            }
        } else {
            packageObject = PackageObjectBuilder.getPackageObjectException(new NotFoundException(), RequestUtils.getRequestedId());
        }
        response.getWriter().write(JsonMapper.INSTANCE.toJson(packageObject));
    }
    /**
     * 直接输出包裹格式响应消息
     *
     * @param body     响应主题
     * @throws IOException 输出信息发生错误时抛出的异常
     */
    public static void writePackageObject(Object body) throws IOException {
        writePackageObject(getResponse(), body);
    }

    /**
     * 响应输出html
     *
     * @param response HttpServletResponse
     * @param body     响应内容
     * @throws IOException 输出信息发生错误时抛出的异常
     */
    public static void writeHtml(HttpServletResponse response, String body) throws IOException {
        response.setContentType(MediaType.TEXT_HTML_VALUE);
        response.setCharacterEncoding(CharEncoding.UTF_8);
        response.setStatus(200);
        response.getWriter().write(body);
    }

    /**
     * 响应输出html
     *
     * @param body 响应内容
     * @throws IOException 输出信息发生错误时抛出的异常
     */
    public static void writeHtml(String body) throws IOException {
        writeHtml(getResponse(), body);
    }
}
