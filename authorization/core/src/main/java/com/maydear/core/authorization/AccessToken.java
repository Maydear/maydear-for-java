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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 访问令牌
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 3255422162591156797L;
    private static final int AUTHORIZATION_VALUE_SPLIT_LENGTH = 2;
    private static final char AUTHORIZATION_VALUE_SEPARATOR_CHAR = ' ';

    /**
     * 令牌类型
     */
    private String type;

    /**
     * 令牌证书
     */
    private String credentials;

    /**
     * 构造数据
     * @param authorizationValue 请求头信息
     * @return 返回访问令牌对象，如果格式不正确则返回null
     */
    public static AccessToken build(String authorizationValue) {
        if (StringUtils.isNotBlank(authorizationValue)) {
            String[] values = StringUtils.split(authorizationValue, AUTHORIZATION_VALUE_SEPARATOR_CHAR);
            if (ObjectUtils.isNotEmpty(values) && values.length == AUTHORIZATION_VALUE_SPLIT_LENGTH) {
                return new AccessToken(values[0], values[1]);
            }
        }
        return null;
    }
}
