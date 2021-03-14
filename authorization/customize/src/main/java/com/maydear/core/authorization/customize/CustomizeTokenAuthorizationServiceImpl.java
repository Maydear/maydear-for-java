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

import com.maydear.core.authorization.AuthorizationIdentity;
import com.maydear.core.authorization.AuthorizationService;
import com.maydear.core.authorization.AuthorizationServiceFactory;
import com.maydear.core.authorization.TicketStore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.PostConstruct;

/**
 * 自定义访问令牌授权服务实现
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Slf4j
public class CustomizeTokenAuthorizationServiceImpl implements AuthorizationService {

    /**
     * 票据仓储
     */
    private TicketStore ticketStore;

    /**
     * 自定义
     * @param ticketStore 票据仓储
     */
    public CustomizeTokenAuthorizationServiceImpl(TicketStore ticketStore) {
        this.ticketStore = ticketStore;
    }

    /**
     * 认证头前缀
     *
     * @return 返回认证头前缀
     */
    @Override
    public String getScheme() {
        return Constants.SCHEME_NAME;
    }

    /**
     * 根据票据信息获取认证用户信息
     *
     * @param ticket 认证票据
     * @return 返回当前认证用户信息
     */
    @Override
    public AuthorizationIdentity getAuthorizationIdentity(String ticket) {
        CustomizeTokenTicket customizeTokenTicket = CustomizeTokenTicket.formAuthorizationTicketValue(ticket);
        if (ObjectUtils.isEmpty(customizeTokenTicket) || ObjectUtils.isEmpty(customizeTokenTicket.getIdentity())) {
            return null;
        }
        return ticketStore.retrieve(customizeTokenTicket.getIdentity());
    }

}
