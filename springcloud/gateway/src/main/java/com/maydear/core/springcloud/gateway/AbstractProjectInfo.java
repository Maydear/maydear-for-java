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
package com.maydear.core.springcloud.gateway;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * 项目信息
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
public abstract class AbstractProjectInfo {

    private static final String ENV_SERVICE_VERSION_NAME  = "SERVICE_VERSION";
    private static final String ENV_BUILD_VERSION_NAME  = "BUILD_VERSION";

    /**
     * 项目名称
     */
    private String name;

    /**
     * 业主
     */
    private String owner;

    /**
     * 供应商
     */
    private String Supplier;

    /**
     * 获取包的版本号
     * @return 返回当前包的版本号
     */
    public String getPackageVersion(){
        Map<String, String> map = System.getenv();
        if(map.containsKey(ENV_SERVICE_VERSION_NAME))
        {
            return map.get(ENV_SERVICE_VERSION_NAME);
        }
        return null;
    }

    public String getPackageBuildVersion(){
        Map<String, String> map = System.getenv();
        if(map.containsKey(ENV_BUILD_VERSION_NAME))
        {
            return map.get(ENV_BUILD_VERSION_NAME);
        }
        return null;
    }
}
