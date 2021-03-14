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

import com.maydear.core.authorization.AuthorizationIdentity;
import com.maydear.core.authorization.AuthorizationIdentityRole;
import com.maydear.core.authorization.AuthorizationService;
import com.maydear.core.authorization.TicketStore;
import com.maydear.core.authorization.jwt.util.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

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
public class JwtAuthorizationServiceImpl implements AuthorizationService {

    /**
     * Jwt选项
     */
    private JwtOptions options;

    /**
     * 存储票据
     */
    private TicketStore ticketStore;

    /**
     *  @param options
     * @param ticketStore
     */
    public JwtAuthorizationServiceImpl(JwtOptions options, TicketStore ticketStore) {
        this.options = options;
        this.ticketStore = ticketStore;
    }

    @Override
    public String getScheme() {
        return Constants.SCHEME_NAME;
    }

    @Override
    public AuthorizationIdentity getAuthorizationIdentity(String ticket) {
        JwtTicket jwtTicket = JwtTokenUtils.decode(ticket, options);
        if (ObjectUtils.isEmpty(jwtTicket)) {
            return null;
        }
        return ticketStore.retrieve(ticket);
    }
}
