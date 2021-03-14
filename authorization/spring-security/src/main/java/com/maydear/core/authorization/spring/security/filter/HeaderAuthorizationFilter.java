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
package com.maydear.core.authorization.spring.security.filter;

import com.google.common.collect.Lists;
import com.maydear.core.authorization.spring.security.AuthorizationIdentityAuthentication;
import com.maydear.core.authorization.spring.security.TokenTicketAuthentication;
import com.maydear.core.authorization.spring.security.exception.StatusCodeAuthenticationException;
import com.maydear.core.authorization.spring.security.exception.UnAuthenticationException;
import com.maydear.core.framework.exception.StatusCodeException;
import com.maydear.core.framework.spring.web.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Header.Authorization 验证
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Slf4j
public class HeaderAuthorizationFilter extends OncePerRequestFilter {

    private RequestMatcher requiresAuthenticationRequestMatcher;
    private List<RequestMatcher> permissiveRequestMatchers;
    private AuthenticationManager authenticationManager;

    private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    public HeaderAuthorizationFilter() {
        this.requiresAuthenticationRequestMatcher = new RequestHeaderRequestMatcher(AUTHORIZATION);
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(authenticationManager, "authenticationManager must be specified");
        Assert.notNull(successHandler, "AuthenticationSuccessHandler must be specified");
        Assert.notNull(failureHandler, "AuthenticationFailureHandler must be specified");
    }

    /**
     * 执行认证
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain) throws ServletException, IOException {
        AuthenticationException failed = null;
        Authentication authResult = null;

        try {
            authResult = processHeaderAuthorization(httpServletRequest);
        } catch (AuthenticationException ex) {
            log.warn(ex.getMessage());
            failed = ex;
        }

        if (permissiveRequest(httpServletRequest)) {
            //如果当前authResult是null的时候需要清掉会话，否则会串人
            if (ObjectUtils.isEmpty(authResult)) {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            if (ObjectUtils.isNotEmpty(failed)) {
                unsuccessfulAuthentication(httpServletRequest, httpServletResponse, failed);
                return;
            } else if (ObjectUtils.isEmpty(authResult)) {
                unsuccessfulAuthentication(httpServletRequest, httpServletResponse, new UnAuthenticationException());
                return;
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    /**
     * 处理请求参数
     *
     * @param httpServletRequest
     * @return
     */
    private Authentication processHeaderAuthorization(HttpServletRequest httpServletRequest) throws AuthenticationException {
        if (!requiresAuthenticationRequestMatcher.matches(httpServletRequest)) {
            return null;
        }

        String authorizationValue = RequestUtils.getHeader(httpServletRequest, AUTHORIZATION);
        if (StringUtils.isNotBlank(authorizationValue)) {
            TokenTicketAuthentication tokenTicketAuthentication = TokenTicketAuthentication.build(authorizationValue);
            if (ObjectUtils.isNotEmpty(tokenTicketAuthentication)) {
                try {
                    Authentication authResult = this.getAuthenticationManager().authenticate(tokenTicketAuthentication);

                    if (authResult instanceof AuthorizationIdentityAuthentication && ObjectUtils.isNotEmpty(authResult.getPrincipal())) {
                        SecurityContextHolder.getContext().setAuthentication(authResult);
                    }
                    return authResult;
                } catch (StatusCodeException exception) {
                    throw new StatusCodeAuthenticationException(exception);
                }
            }
        }

        return null;
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }

    public void setPermissiveUrl(String... urls) {
        if (permissiveRequestMatchers == null) {
            permissiveRequestMatchers = Lists.newArrayList();
        }
        for (String url : urls) {
            permissiveRequestMatchers.add(new AntPathRequestMatcher(url));
        }
    }

    /**
     * 请求匹配头
     *
     * @param request
     * @return
     */
    private boolean permissiveRequest(HttpServletRequest request) {
        if (permissiveRequestMatchers == null) {
            return false;
        }
        for (RequestMatcher permissiveMatcher : permissiveRequestMatchers) {
            if (permissiveMatcher.matches(request)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return
     */
    protected AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    /**
     * @param authenticationManager
     */
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        Assert.notNull(authenticationManager, "authenticationManager must be specified");
        this.authenticationManager = authenticationManager;
    }

    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        Assert.notNull(successHandler, "未配置 successHandler。");
        this.successHandler = successHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "未配置 failureHandler。");
        this.failureHandler = failureHandler;
    }

}
