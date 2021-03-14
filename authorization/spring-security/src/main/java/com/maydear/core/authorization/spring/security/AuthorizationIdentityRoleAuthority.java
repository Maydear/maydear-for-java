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

import com.maydear.core.authorization.AuthorizationIdentityRole;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * 角色授权
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@NoArgsConstructor
public class AuthorizationIdentityRoleAuthority extends AuthorizationIdentityRole implements GrantedAuthority {

    private AuthorizationIdentityRoleAuthority(AuthorizationIdentityRole role) {
        this.setName(role.getName());
        this.setDescription(role.getDescription());
    }

    @Override
    public String getAuthority() {
        return this.getName();
    }

    public static AuthorizationIdentityRoleAuthority build(AuthorizationIdentityRole role) {
        return new AuthorizationIdentityRoleAuthority(role);
    }
}
