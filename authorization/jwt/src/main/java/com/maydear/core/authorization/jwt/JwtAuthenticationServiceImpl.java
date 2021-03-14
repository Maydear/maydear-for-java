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
package com.maydear.core.authorization.jwt;

import com.maydear.core.authorization.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

/**
 * Jwt安全认证
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Slf4j
public class JwtAuthenticationServiceImpl implements AuthenticationService {

    /**
     * Jwt选项
     */
    private JwtOptions options;

    /**
     * 存储票据
     */
    private TicketStore ticketStore;

    /**
     * @param options
     * @param ticketStore
     */
    public JwtAuthenticationServiceImpl(JwtOptions options, TicketStore ticketStore) {
        this.options = options;
    }

    /**
     * 认证架构
     *
     * @return 返回认证头前缀
     */
    @Override
    public String getScheme() {
        return Constants.SCHEME_NAME;
    }

    /**
     * 登录
     *
     * @param identity 身份认证
     * @param roles    身份角色
     * @param payload  身份其他载荷信息
     * @return 返回认证票据
     */
    @Override
    public String signIn(Serializable identity, List<AuthorizationIdentityRole> roles, Object payload) {
        JwtTicket jwtTicket = new JwtTicket(identity.toString(), roles, payload);
        ticketStore.store(jwtTicket.buildAuthorizationIdentity(options));
        return MessageFormat.format("{0} {1}", getScheme(), jwtTicket.generateAuthorizationTicketValue(options));
    }

    /**
     * 登出指定用户
     *
     * @param identity 身份认证
     */
    @Override
    public void signOut(Serializable identity) {
        ticketStore.remove(identity.toString());
    }
}
