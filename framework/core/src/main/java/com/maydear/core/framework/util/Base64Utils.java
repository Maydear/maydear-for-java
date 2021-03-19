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
package com.maydear.core.framework.util;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Base64;

/**
 * Base64 工具类
 * com.google.common.base
 * (针对jdk1.8进行优化)
 *
 * @author kelvin.liang
 */
public class Base64Utils {

    /**
     * 防止静态类被实例化
     */
    private Base64Utils() {
        throw new IllegalStateException("Utility class");
    }

    private static final String EXTENSION_JPG = "jpg";
    private static final String EXTENSION_JPEG = "jpeg";
    private static final String EXTENSION_GIF = "gif";
    private static final String EXTENSION_ICO = "ico";

    /**
     * 将明文字符串进行Base64编码
     *
     * @param str 需要编码的字符串
     * @return Base64编码后的字符串
     */
    public static String encode(String str) {
        return Base64.getUrlEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将指定路径的图片进行Base64编码（gif/png/jpg/ico）
     *
     * @return Base64编码后的字符串
     */
    public static String encodeImage(String strPath, String originalFilename) {
        String base64Content = Base64.getUrlEncoder().encodeToString(LocalFileUtils.read(Path.of(strPath)));
        if (StringUtils.isNotBlank(originalFilename)) {
            String extName = StringUtils.substringAfterLast(originalFilename, ".");

            //data:image/gif;base64,base64编码的gif图片数据
            //data:image/png;base64,base64编码的png图片数据
            //data:image/jpeg;base64,base64编码的jpeg图片数据
            //data:image/x-icon;base64,base64编码的icon图片数据
            if(StringUtils.equalsAnyIgnoreCase(extName,EXTENSION_GIF)){
                return "data:image/"+EXTENSION_GIF+";base64," + base64Content;
            }

            if(StringUtils.equalsAnyIgnoreCase(extName,EXTENSION_JPEG) || StringUtils.equalsAnyIgnoreCase(extName,EXTENSION_JPG)){
                return "data:image/"+EXTENSION_JPG+";base64," + base64Content;
            }
            if(StringUtils.equalsAnyIgnoreCase(extName,EXTENSION_ICO)){
                return "data:image/x-icon;base64," + base64Content;
            }
        }

        return "data:image/png;base64," + base64Content;
    }

    /**
     * 将Base64编码字符串转明文
     *
     * @param str Base64编码字符串
     * @return 明文字符串
     */
    public static String decode(String str) {
        return new String(Base64.getUrlDecoder().decode(str), StandardCharsets.UTF_8);
    }
}
