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
package com.maydear.core.authorization;

import lombok.*;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;

/**
 * 许可清单
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Permission implements Serializable {

    /**
     * 许可名称
     */
    private String name;

    /**
     * 资源路径
     */
    private String uri;

    /**
     * 请求方式
     */
    private String method;

    /**
     *许可角色
     */
    private AuthorizationIdentityRole role;

    /**
     * 获取角色标记
     * @return 返回角色标记
     */
    public String getRoleName()
    {
        if(ObjectUtils.isEmpty(role))
        {
            return "";
        }
        return role.getName();
    }

    /**
     * 获取角色中文名称
     * @return 返回许可角色的中文名称
     */
    public String getRoleDisplayText()
    {
        if(ObjectUtils.isEmpty(role))
        {
            return "";
        }
        return role.getDisplayText();
    }

    /**
     * 获取角色中文名称
     * @return 返回许可角色的中文名称
     */
    public String getRoleDescription()
    {
        if(ObjectUtils.isEmpty(role))
        {
            return "";
        }
        return role.getDescription();
    }
}
