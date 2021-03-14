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
package com.maydear.core.framework.spring.web.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 浏览器工具类
 *
 * @author phil
 * @version 1.0.0
 */
@Slf4j
public final class BrowserUtils {

    /**
     * 静态工具类不应该被实例化
     */
    private BrowserUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * IE或Edga浏览器的 User-Agent 关键字
     */
    private static final String[] MS_BROWSE_AGENT_KEYWORD = {"MSIE", "GECKO", "RV:11"};

    /**
     * 根据 Useg-Agent 判断是否微软浏览器(Edga/IE)
     *
     * @param userAgent Useg-Agent
     * @return boolean
     */
    public static boolean isMicrosoftBrowser(String userAgent) {
        if (StringUtils.isBlank(userAgent)) {
            throw new NullPointerException("参数[userAgent]不能为空。");
        }

        for (String keyword : MS_BROWSE_AGENT_KEYWORD) {
            if (StringUtils.contains(userAgent.toUpperCase(), keyword)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取文件下载时的显示名称
     *
     * @param userAgent Useg-Agent
     * @param filename  原始文件名
     * @return 转码后的文件名
     */
    public static String getDownloadName(String userAgent, String filename) {
        if (StringUtils.isBlank(userAgent) || StringUtils.isBlank(filename)) {
            throw new NullPointerException("参数[userAgent]或[filename]不能为空。");
        }
        if (isMicrosoftBrowser(userAgent)) {
            try {
                filename = URLEncoder.encode(filename, StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                log.error("文件名转码时发生错误。", e);
            }
        } else {
            filename = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }
        return filename;
    }
}
