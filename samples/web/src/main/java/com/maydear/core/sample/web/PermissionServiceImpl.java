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
package com.maydear.core.sample.web;

import com.maydear.core.authorization.Permission;
import com.maydear.core.authorization.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 个性化许可服务
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Slf4j
@Component
public class PermissionServiceImpl implements PermissionService {
    public PermissionServiceImpl() {
        log.info("PermissionServiceImpl 实例化");
    }

    /**
     * 获取所有许可清单
     *
     * @return 返回许可清单列表
     */
    @Override
    public List<Permission> getAll() {
        return null;
    }

    /**
     * 获取指定角色的许可清单
     *
     * @param roleName 角色标志
     * @return 返回许可清单列表
     */
    @Override
    public List<Permission> getListByRole(String roleName) {
        return null;
    }
}
