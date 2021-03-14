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
import org.springframework.security.core.GrantedAuthority;

/**
 * security的GrantedAuthority实现
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class GrantedAuthorityRole extends AuthorizationIdentityRole implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return getName();
    }

    /**
     * 后遭
     * @param role
     */
    public GrantedAuthorityRole(AuthorizationIdentityRole role){
        setDescription(role.getDescription());
        setDisplayText(role.getDisplayText());
        setName(role.getName());
    }
}
