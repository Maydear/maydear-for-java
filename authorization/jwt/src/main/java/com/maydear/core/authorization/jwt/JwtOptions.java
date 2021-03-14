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
package com.maydear.core.authorization.jwt;

import com.maydear.core.authorization.AbstractAuthorizationOptions;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Jwt选项集
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class JwtOptions extends AbstractAuthorizationOptions implements Serializable {

    private static final long serialVersionUID = 7528201018044130609L;

    /**
     * 默认密码
     */
    public static final String DEFAULT_SECRET = "b4a752deb4c9d5943188202bb87435831368dfc5c69a80c30f06278c32d6970c";

    /**
     * 默认发行方
     */
    public static final String DEFAULT_ISSUER = "maydear.com";

    /**
     * 证书
     */
    private String secret;

    /**
     * 发行方
     */
    private String issuer;

}
