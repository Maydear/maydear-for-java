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
package com.maydear.core.authorization.customize;

import com.maydear.core.authorization.*;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

/**
 * 访问令牌认证服务实现
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class CustomizeTokenAuthenticationServiceImpl implements AuthenticationService {

    private TicketStore ticketStore;

    public CustomizeTokenAuthenticationServiceImpl(TicketStore ticketStore) {
        this.ticketStore = ticketStore;
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
        CustomizeTokenTicket customizeTokenTicket = CustomizeTokenTicket.newInstance(identity.toString());
        AuthorizationIdentity authorizationIdentity = customizeTokenTicket.buildAuthorizationIdentity(roles, payload);
        ticketStore.store(authorizationIdentity);
        return MessageFormat.format("{0} {1}", getScheme(), authorizationIdentity.getTicket());
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
