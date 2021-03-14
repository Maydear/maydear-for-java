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
package com.maydear.core.authorization.spring.security.handler;

import com.maydear.core.authorization.spring.security.exception.StatusCodeAuthenticationException;
import com.maydear.core.framework.exception.StatusCodeException;
import com.maydear.core.framework.spring.web.util.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权认证失败
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class HeaderAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(exception instanceof StatusCodeAuthenticationException)
        {
            StatusCodeAuthenticationException statusCodeAuthenticationException =(StatusCodeAuthenticationException)exception;
            ResponseUtils.writePackageObject(response,new StatusCodeException(statusCodeAuthenticationException.getStatusCode(),statusCodeAuthenticationException.getMessage()));
        }
        else
        {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

    }
}
