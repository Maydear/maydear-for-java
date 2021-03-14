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
package com.maydear.core.sample.web.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.maydear.core.authorization.AccessToken;
import com.maydear.core.authorization.AuthenticationServiceFactory;
import com.maydear.core.authorization.AuthorizationIdentity;
import com.maydear.core.authorization.AuthorizationServiceFactory;
import com.maydear.core.authorization.annotation.AllowAnonymous;
import com.maydear.core.authorization.annotation.Authorize;
import com.maydear.core.authorization.spring.security.util.AuthenticationUtils;
import com.maydear.core.authorization.spring.security.util.SecurityContextHolderUtils;
import com.maydear.core.sample.web.model.ClassicLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 类描述
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping(value = "v1/auth")
public class AuthController {
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public AuthController(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @GetMapping(value = "getAllUrl")
    @AllowAnonymous
    public Object getAllUrl() {

        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

        List<Map<String, String>> list = Lists.newArrayList();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            Map<String, String> map1 = Maps.newHashMap();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            if (isAllowAnonymous(method)) {
                PatternsRequestCondition p = info.getPatternsCondition();
                for (String url : p.getPatterns()) {
                    map1.put("url", url);
                }
                // 方法名
                Method methods = method.getMethod();
                Class<?> classInstance = methods.getDeclaringClass();
                // 类名
                map1.put("className", classInstance.getName());
                map1.put("method", methods.getName());
                RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
                for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                    map1.put("type", requestMethod.toString());
                }

                list.add(map1);
            }

        }

        return list;
    }


    private boolean isAllowAnonymous(HandlerMethod handlerMethod) {
        // 方法名
        Method method = handlerMethod.getMethod();
        Class<?> classInstance = method.getDeclaringClass();
        if (classInstance.isAnnotationPresent(AllowAnonymous.class)) {
            return !method.isAnnotationPresent(Authorize.class);
        }
        return method.isAnnotationPresent(AllowAnonymous.class);

    }

    /**
     * @param login
     * @return
     */
    @PostMapping(path = "login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @AllowAnonymous
    public AccessToken login(@RequestBody(required = false) @Valid ClassicLogin login) {
        return AccessToken.build(AuthenticationUtils.getCustomizeAuthentication().signIn(login.getMobile(), null, null));
    }

    /**
     * @param login
     * @return
     */
    @PostMapping(path = "jwt", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @AllowAnonymous
    public AccessToken jwt(@RequestBody(required = false) @Valid ClassicLogin login) {
        return AccessToken.build(AuthenticationUtils.getJwtAuthentication().signIn(login.getMobile(), null, null));
    }


    /**
     * @return
     */
    @GetMapping(path = "info")
    public AuthorizationIdentity info() {
        return SecurityContextHolderUtils.getCurrentAuthorizationIdentity();
    }
}
