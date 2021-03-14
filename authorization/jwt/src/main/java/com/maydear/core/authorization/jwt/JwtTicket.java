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
import com.maydear.core.authorization.jwt.util.JwtTokenUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * JWT票据
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
public class JwtTicket implements Serializable {

    private static final long serialVersionUID = -180989421874487380L;
    /**
     * 身份标识
     */
    private String identity;

    /**
     * 载荷信息
     */
    private Object payload;

    /**
     * 授权书身份角色
     */
    private List<AuthorizationIdentityRole> roles;

    /**
     * 构造jwt票据容器实体
     *
     * @param identity
     * @param roles
     * @param payload
     */
    public JwtTicket(String identity, List<AuthorizationIdentityRole> roles, Object payload) {
        this.identity = identity;
        this.roles = roles;
        this.payload = payload;
    }

    /**
     * 从授权书访问票据值构造访问令牌票据实体
     *
     * @param ticketValue 票据值
     * @return 返回授权身份对象
     */
    public static JwtTicket formAuthorizationTicketValue(String ticketValue, JwtOptions jwtOptions) {
        return JwtTokenUtils.decode(ticketValue, jwtOptions);
    }

    public String generateAuthorizationTicketValue(JwtOptions jwtOptions) {
        return JwtTokenUtils.encode(this, jwtOptions);
    }

    /**
     * 构造授权书身份标识
     *
     * @return 返回授权书身份标识
     */
    public AuthorizationIdentity buildAuthorizationIdentity(JwtOptions jwtOptions) {
        return AuthorizationIdentity.builder()
            .ticket(generateAuthorizationTicketValue(jwtOptions))
            .identity(identity)
            .payload(payload)
            .roles(roles)
            .build();
    }
}
