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
package com.maydear.core.springboot.web.util;

import com.maydear.core.springboot.web.AbstractProjectInfo;
import com.maydear.framework.core.spring.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 启动版权信息工具类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Slf4j
public class StartupCopyrightUtils {

    /**
     * 静态工具类不应该被实例化
     */
    private StartupCopyrightUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 输出版权信息
     *
     * @param projectInfo 项目信息
     */
    public static void outputCopyrigh(AbstractProjectInfo projectInfo) {
        log.info("----------------------------------------------------");
        String activeProfile = SpringContextUtils.getActiveProfile();
        if (projectInfo != null) {
            log.info(projectInfo.getName());
            if (StringUtils.isNotBlank(projectInfo.getOwner())) {
                log.info(projectInfo.getOwner() + "版权所有");
            }
            if (StringUtils.isNotBlank(projectInfo.getPackageVersion())) {
                log.info("version:" + projectInfo.getPackageVersion());
            }
            if (StringUtils.isNotBlank(projectInfo.getPackageBuildVersion())) {
                log.info("Build(commit):" + projectInfo.getPackageBuildVersion());
            }
            if (StringUtils.isNotBlank(activeProfile)) {
                log.info("Profile:" + activeProfile);
            }
            if (StringUtils.isNotBlank(projectInfo.getSupplier())) {
                log.info("由" + projectInfo.getSupplier() + "提供技术支持");
            }
        } else {
            log.info(SpringContextUtils.getApplicationName() + "启动完成");
        }
        log.info("----------------------------------------------------");
    }

    /**
     * 输出版权信息
     */
    public static void outputCopyrigh() {
        AbstractProjectInfo projectInfo = SpringContextUtils.getBean(AbstractProjectInfo.class);

        outputCopyrigh(projectInfo);
    }
}
