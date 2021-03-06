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
 * RestController??????????????????
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    /**
     * MediaType????????????
     */
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public PackageObject httpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException e) {
        log.debug("RestController?????????????????????httpMediaTypeNotSupportedExceptionHandler", e);

        StringBuilder sb = new StringBuilder();
        sb.append("?????????????????????????????????");
        sb.append(e.getContentType());
        sb.append(",?????????????????????????????????????????????");
        sb.append(StringExtensionUtils.join(",", e.getSupportedMediaTypes().stream().map(MediaType::toString).collect(Collectors.toList())));
        return PackageObjectBuilder.getPackageObjectException(new BadRequestException(sb.toString()), RequestUtils.getRequestedId());
    }

    /**
     * RequestMethod????????????
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public PackageObject httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.debug("RestController?????????????????????HttpRequestMethodNotSupportedException", e);

        StringBuilder sb = new StringBuilder();
        sb.append("??????????????????????????????");
        sb.append(e.getMethod());
        sb.append(",???????????????????????????????????????");
        sb.append(StringExtensionUtils.join(",", e.getSupportedMethods()));
        return PackageObjectBuilder.getPackageObjectException(new BadRequestException(sb.toString()), RequestUtils.getRequestedId());
    }

    /**
     * ??????????????????
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public PackageObject httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.debug("RestController?????????????????????HttpMessageNotReadableException", e);
        if (e.getCause() instanceof InvalidFormatException) {
            StringBuilder sb = new StringBuilder();
            InvalidFormatException invalidFormatException = (InvalidFormatException) e.getCause();
            sb.append("?????????????????????{\"");
            sb.append(invalidFormatException.getPath().get(0).getFieldName());
            sb.append("\":\"");
            sb.append(invalidFormatException.getValue());
            sb.append("\"},????????????????????????");
            sb.append(invalidFormatException.getTargetType().getName());
            sb.append("??????");
            return PackageObjectBuilder.getPackageObjectException(new UnprocessableEntityException(sb.toString()), RequestUtils.getRequestedId());
        }
        return PackageObjectBuilder.getPackageObjectException(new UnprocessableEntityException(e), RequestUtils.getRequestedId());
    }

    /**
     * ????????????????????????
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public PackageObject methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.debug("RestController?????????????????????MethodArgumentNotValidException", e);
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        sb.append("?????????????????????,");
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
     * ????????????????????????
     */
    @ExceptionHandler(value = MissingMatrixVariableException.class)
    public PackageObject missingMatrixVariableExceptionHandler(MissingMatrixVariableException e) {
        log.debug("RestController?????????????????????missingMatrixVariableExceptionHandler", e);
        StringBuilder sb = new StringBuilder();
        sb.append("???????????????????????????");
        sb.append(e.getVariableName());
        sb.append(",???????????????????????????????????????");
        sb.append(e.getParameter().getNestedParameterType().getSimpleName());
        return PackageObjectBuilder.getPackageObjectException(new BadRequestException(sb.toString()), RequestUtils.getRequestedId());
    }

    /**
     * ????????????????????????????????????
     */
    @ExceptionHandler(value = MissingPathVariableException.class)
    public PackageObject missingPathVariableExceptionHandler(MissingPathVariableException e) {
        log.debug("RestController?????????????????????missingPathVariableExceptionHandler", e);
        StringBuilder sb = new StringBuilder();
        sb.append("???????????????????????????");
        sb.append(e.getVariableName());
        sb.append(",???????????????????????????????????????");
        sb.append(e.getParameter().getNestedParameterType().getSimpleName());
        return PackageObjectBuilder.getPackageObjectException(new BadRequestException(sb.toString()), RequestUtils.getRequestedId());
    }

    /**
     * ???????????????????????????
     *
     * @param e ?????????????????????
     * @return ????????????
     */
    @ExceptionHandler(StatusCodeException.class)
    public PackageObject handleRuntimeException(StatusCodeException e) {
        log.warn(e.getMessage(), e);
        return PackageObjectBuilder.getPackageObjectException(e, RequestUtils.getRequestedId());
    }

    /**
     * By default when the DispatcherServlet can't find a handler for a request it sends a 404 response Exception
     *
     * @param e ??????????????????
     * @return ????????????
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public PackageObject noHandlerFoundException(NoHandlerFoundException e) {
        log.debug("Controller?????????????????????Exception", e);
        return PackageObjectBuilder.getPackageObjectException(new NotFoundRequestUrlException(e.getRequestURL()), RequestUtils.getRequestedId());
    }

    /**
     * ?????? RuntimeException
     *
     * @param e ???????????????
     * @return ????????????
     */
    @ExceptionHandler(RuntimeException.class)
    public PackageObject handleRuntimeException(RuntimeException e) {
        log.debug("RestController?????????????????????RuntimeException", e);
        log.error(e.getMessage(), e);

        return PackageObjectBuilder.getPackageObjectException(new SystemErrorException(), RequestUtils.getRequestedId());
    }

    /**
     * ?????? Exception
     *
     * @param e ??????????????????
     * @return ????????????
     */
    @ExceptionHandler(Exception.class)
    public PackageObject exceptionHandler(Exception e) {
        log.debug("RestController?????????????????????Exception", e);
        log.error(e.getMessage(), e);
        return PackageObjectBuilder.getPackageObjectException(new SystemErrorException(), RequestUtils.getRequestedId());
    }


}
