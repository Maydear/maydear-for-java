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

import com.maydear.core.authorization.Permission;
import com.maydear.core.authorization.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 从读取权限。
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Slf4j
@Component
public class MyInvocationSecurityMetadataSource extends AbstractInvocationSecurityMetadataSource {

    private final PermissionService permissionService;

    @Autowired(required = false)
    public MyInvocationSecurityMetadataSource(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    protected List<ConfigAttribute> getPermission(HttpServletRequest request) {

        if (ObjectUtils.isEmpty(permissionService)) {
            log.error("PermissionService is not found implements");
            return null;
        }

        List<Permission> permissionList = permissionService.getAll();
        if (CollectionUtils.isNotEmpty(permissionList)) {
            List<String> matcheRolesName = permissionList.stream().distinct().filter(p ->
                    new AntPathRequestMatcher(p.getUri(), p.getMethod()).matches(request)
            ).map(Permission::getName).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(matcheRolesName)) {
                String[] rolesName = new String[matcheRolesName.size()];
                for (int i = 0; i < matcheRolesName.size(); i++) {
                    rolesName[i] = "ROLE_" + matcheRolesName.get(i);
                }
                return SecurityConfig.createList(rolesName);
            }
        }
        return null;
    }
}
