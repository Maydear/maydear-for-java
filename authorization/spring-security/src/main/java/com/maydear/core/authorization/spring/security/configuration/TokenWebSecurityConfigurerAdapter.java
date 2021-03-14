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

import com.google.common.collect.Lists;
import com.maydear.core.authorization.spring.security.AnnotationPermissiveRequestUrls;
import com.maydear.core.authorization.spring.security.TokenAccessDeniedHandler;
import com.maydear.core.authorization.spring.security.TokenAuthenticationEntryPoint;
import com.maydear.core.authorization.spring.security.filter.OptionsRequestFilter;
import com.maydear.core.authorization.spring.security.provider.MaydearAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * 抽象Token配置
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@EnableWebSecurity
public class TokenWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final AnnotationPermissiveRequestUrls annotationPermissiveRequestUrls;

    public TokenWebSecurityConfigurerAdapter(AnnotationPermissiveRequestUrls annotationPermissiveRequestUrls) {
        this.annotationPermissiveRequestUrls = annotationPermissiveRequestUrls;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getApplicationContext().getBean(MaydearAuthenticationProvider.class));
    }

    /**
     * 获取例外请求地址
     * @return 返回例外请求地址
     */
    public String[] getPermissiveRequestUrls() {
        List<String> urlLists = Lists.newArrayList(annotationPermissiveRequestUrls.getAllowAnonymousUrl());

        return urlLists.toArray(new String[urlLists.size()]);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin().disable()
                .sessionManagement().disable()
                .httpBasic().disable()
                .cors()
                .and()
                .headers().addHeaderWriter(getHeadersWriter())
                .and()
                .addFilterAfter(new OptionsRequestFilter(), CorsFilter.class)
                .apply(new HeaderAuthorizationConfigurer<>())
                .permissiveRequestUrls(getPermissiveRequestUrls())
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new TokenAccessDeniedHandler())
                .authenticationEntryPoint(new TokenAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers(getPermissiveRequestUrls()).permitAll()
                .anyRequest().authenticated();
    }

    protected StaticHeadersWriter getHeadersWriter() {
        return new StaticHeadersWriter(Arrays.asList(
                new Header("Access-control-Allow-Origin", "*"),
                new Header("Access-Control-Expose-Headers", "Authorization")));
    }
}
