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
package com.maydear.core.authorization.spring.security.configuration;

import com.maydear.core.authorization.spring.security.filter.HeaderAuthorizationFilter;
import com.maydear.core.authorization.spring.security.handler.HttpStatusCodeAuthenticationFailureHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * Authorization认证配置
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class HeaderAuthorizationConfigurer<T extends HeaderAuthorizationConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {
    private HeaderAuthorizationFilter headerAuthorizationFilter;

    public HeaderAuthorizationConfigurer() {
        this.headerAuthorizationFilter = new HeaderAuthorizationFilter();
    }

    @Override
    public void configure(B http) throws Exception {
        headerAuthorizationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        headerAuthorizationFilter.setAuthenticationFailureHandler(new HttpStatusCodeAuthenticationFailureHandler());

        HeaderAuthorizationFilter filter = postProcess(headerAuthorizationFilter);
        http.addFilterBefore(filter, LogoutFilter.class);
    }

    public HeaderAuthorizationConfigurer<T, B> permissiveRequestUrls(String ... urls){
        headerAuthorizationFilter.setPermissiveUrl(urls);
        return this;
    }
}