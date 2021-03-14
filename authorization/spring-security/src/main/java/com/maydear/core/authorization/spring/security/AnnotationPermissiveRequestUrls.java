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
package com.maydear.core.authorization.spring.security;

import com.google.common.collect.Lists;
import com.maydear.core.authorization.annotation.AllowAnonymous;
import com.maydear.core.authorization.annotation.Authorize;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 注解方式获取例外的url
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Component
public class AnnotationPermissiveRequestUrls {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public AnnotationPermissiveRequestUrls(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    /**
     * 获取所有开放的url
     *
     * @return 获取所有开放的url
     */
    public List<String> getAllowAnonymousUrl() {
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        List<String> allowAnonymousUrlList = Lists.newArrayList();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            if (isAllowAnonymous(method)) {
                PatternsRequestCondition p = info.getPatternsCondition();
                for (String url : p.getPatterns()) {
                    allowAnonymousUrlList.add(url);
                }
            }

        }

        return allowAnonymousUrlList;
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
}
