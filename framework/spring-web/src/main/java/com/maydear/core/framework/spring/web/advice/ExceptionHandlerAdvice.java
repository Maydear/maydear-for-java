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

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.maydear.core.framework.PackageObject;
import com.maydear.core.framework.PackageObjectBuilder;
import com.maydear.core.framework.exception.BadRequestException;
import com.maydear.core.framework.exception.StatusCodeException;
import com.maydear.core.framework.exception.SystemErrorException;
import com.maydear.core.framework.exception.UnprocessableEntityException;
import com.maydear.core.framework.spring.web.exception.NotFoundRequestUrlException;
import com.maydear.core.framework.spring.web.util.RequestUtils;
import com.maydear.core.framework.util.StringExtensionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingMatrixVariableException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * RestController异常拦截处理
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    /**
     * MediaType请求异常
     */
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public PackageObject httpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException e) {
        log.debug("RestController异常拦截处理：httpMediaTypeNotSupportedExceptionHandler", e);

        StringBuilder sb = new StringBuilder();
        sb.append("当前资源路径请求类型：");
        sb.append(e.getContentType());
        sb.append(",当前资源路径可支持的请求类型：");
        sb.append(StringExtensionUtils.join(",", e.getSupportedMediaTypes().stream().map(MediaType::toString).collect(Collectors.toList())));
        return PackageObjectBuilder.getPackageObjectException(new BadRequestException(sb.toString()), RequestUtils.getRequestedId());
    }

    /**
     * RequestMethod请求异常
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public PackageObject httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.debug("RestController异常拦截处理：HttpRequestMethodNotSupportedException", e);

        StringBuilder sb = new StringBuilder();
        sb.append("当前资源路径不支持：");
        sb.append(e.getMethod());
        sb.append(",当前资源路径可支持的请求：");
        sb.append(StringExtensionUtils.join(",", e.getSupportedMethods()));
        return PackageObjectBuilder.getPackageObjectException(new BadRequestException(sb.toString()), RequestUtils.getRequestedId());
    }

    /**
     * 输入参数异常
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public PackageObject httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.debug("RestController异常拦截处理：HttpMessageNotReadableException", e);
        if (e.getCause() instanceof InvalidFormatException) {
            StringBuilder sb = new StringBuilder();
            InvalidFormatException invalidFormatException = (InvalidFormatException) e.getCause();
            sb.append("参数解析异常：{\"");
            sb.append(invalidFormatException.getPath().get(0).getFieldName());
            sb.append("\":\"");
            sb.append(invalidFormatException.getValue());
            sb.append("\"},无法转换为类型“");
            sb.append(invalidFormatException.getTargetType().getName());
            sb.append("”。");
            return PackageObjectBuilder.getPackageObjectException(new UnprocessableEntityException(sb.toString()), RequestUtils.getRequestedId());
        }
        return PackageObjectBuilder.getPackageObjectException(new UnprocessableEntityException(e), RequestUtils.getRequestedId());
    }

    /**
     * 输入参数校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public PackageObject methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.debug("RestController异常拦截处理：MethodArgumentNotValidException", e);
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        sb.append("参数验证不通过,");
        if (bindingResult.hasErrors()) {
            Map<String, List<String>> fieldErrors = Maps.newHashMap();
            for (FieldError error : bindingResult.getFieldErrors()) {
                List<String> errors = Lists.newArrayList();
                if (fieldErrors.containsKey(error.getField())) {
                    errors = fieldErrors.get(error.getField());
                }
                errors.add(error.getDefaultMessage());
                fieldErrors.put(error.getField(), errors);
            }
            for (String fieldErrorKey : fieldErrors.keySet()) {
                sb.append("{ \"" + fieldErrorKey + "\": [\"" + StringExtensionUtils.join("\",\"", fieldErrors.get(fieldErrorKey)) + "\"] }");
            }
            return PackageObjectBuilder.getPackageObjectException(new UnprocessableEntityException(sb.toString()), RequestUtils.getRequestedId());
        }
        return PackageObjectBuilder.getPackageObjectException(new UnprocessableEntityException(e), RequestUtils.getRequestedId());
    }

    /**
     * 输入参数校验异常
     */
    @ExceptionHandler(value = MissingMatrixVariableException.class)
    public PackageObject missingMatrixVariableExceptionHandler(MissingMatrixVariableException e) {
        log.debug("RestController异常拦截处理：missingMatrixVariableExceptionHandler", e);
        StringBuilder sb = new StringBuilder();
        sb.append("当前资源路径参数：");
        sb.append(e.getVariableName());
        sb.append(",当前资源路径参数支持类型：");
        sb.append(e.getParameter().getNestedParameterType().getSimpleName());
        return PackageObjectBuilder.getPackageObjectException(new BadRequestException(sb.toString()), RequestUtils.getRequestedId());
    }

    /**
     * 输入路径参数参数校验异常
     */
    @ExceptionHandler(value = MissingPathVariableException.class)
    public PackageObject missingPathVariableExceptionHandler(MissingPathVariableException e) {
        log.debug("RestController异常拦截处理：missingPathVariableExceptionHandler", e);
        StringBuilder sb = new StringBuilder();
        sb.append("当前资源路径参数：");
        sb.append(e.getVariableName());
        sb.append(",当前资源路径参数支持类型：");
        sb.append(e.getParameter().getNestedParameterType().getSimpleName());
        return PackageObjectBuilder.getPackageObjectException(new BadRequestException(sb.toString()), RequestUtils.getRequestedId());
    }

    /**
     * 处理含有状态码异常
     *
     * @param e 含有状态码异常
     * @return 响应信息
     */
    @ExceptionHandler(StatusCodeException.class)
    public PackageObject handleRuntimeException(StatusCodeException e) {
        log.warn(e.getMessage(), e);
        return PackageObjectBuilder.getPackageObjectException(e, RequestUtils.getRequestedId());
    }

    /**
     * By default when the DispatcherServlet can't find a handler for a request it sends a 404 response Exception
     *
     * @param e 非运行时异常
     * @return 响应信息
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public PackageObject noHandlerFoundException(NoHandlerFoundException e) {
        log.debug("Controller异常拦截处理：Exception", e);
        return PackageObjectBuilder.getPackageObjectException(new NotFoundRequestUrlException(e.getRequestURL()), RequestUtils.getRequestedId());
    }

    /**
     * 处理 RuntimeException
     *
     * @param e 运行时异常
     * @return 响应信息
     */
    @ExceptionHandler(RuntimeException.class)
    public PackageObject handleRuntimeException(RuntimeException e) {
        log.debug("RestController异常拦截处理：RuntimeException", e);
        log.error(e.getMessage(), e);

        return PackageObjectBuilder.getPackageObjectException(new SystemErrorException(), RequestUtils.getRequestedId());
    }

    /**
     * 处理 Exception
     *
     * @param e 非运行时异常
     * @return 响应信息
     */
    @ExceptionHandler(Exception.class)
    public PackageObject exceptionHandler(Exception e) {
        log.debug("RestController异常拦截处理：Exception", e);
        log.error(e.getMessage(), e);
        return PackageObjectBuilder.getPackageObjectException(new SystemErrorException(), RequestUtils.getRequestedId());
    }


}
