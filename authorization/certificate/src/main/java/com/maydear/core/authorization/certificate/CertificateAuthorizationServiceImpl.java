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

package com.maydear.core.authorization.certificate;

import com.maydear.core.authorization.AuthorizationIdentity;
import com.maydear.core.authorization.AuthorizationService;
import org.apache.commons.lang3.ObjectUtils;

/**
 * 证书授权
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class CertificateAuthorizationServiceImpl implements AuthorizationService {

    /**
     * 证书选项
     */
    private final CertificateAuthorizationOptions options;

    /**
     * 证书授权
     *
     * @param options 证书选项
     */
    public CertificateAuthorizationServiceImpl(CertificateAuthorizationOptions options) {
        this.options = options;
    }

    /**
     * 认证头前缀
     *
     * @return 返回认证头前缀
     */
    @Override
    public String getScheme() {
        return Constants.SCHEME_NAME;
    }

    /**
     * 根据票据信息获取认证用户信息
     *
     * @param ticket 认证票据
     * @return 返回当前认证用户信息
     */
    @Override
    public AuthorizationIdentity getAuthorizationIdentity(String ticket) {
        CertificateTicket certificateTicket = CertificateTicket.formAuthorizationTicketValue(ticket, options);

        if (ObjectUtils.isEmpty(certificateTicket)) {
            return null;
        }
        return certificateTicket.toAuthorizationIdentity();
    }
}
