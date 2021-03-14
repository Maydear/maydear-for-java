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
import com.maydear.core.authorization.AuthorizationIdentityRole;
import com.maydear.core.framework.exception.EncryptionErrorException;
import com.maydear.core.framework.jackson.mapper.JsonMapper;
import com.maydear.core.framework.util.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;

/**
 * 访问令牌票据实体
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Slf4j
@Data
public class CertificateTicket {

    /**
     * 授权书访问票据分隔符
     */
    private static final char AUTHORIZATION_TICKET_SEPARATOR_CHAR = '.';

    /**
     * 格式长度
     */
    private static final int SEPARATOR_LENGTH = 3;

    /**
     * 应用名称的位置
     */
    private static final int AUTHORIZATION_TICKET_NAME_VALUE_INDEX = 0;

    /**
     * 数据的位置
     */
    private static final int AUTHORIZATION_TICKET_VALUE_INDEX = 1;

    /**
     * 令牌数据的票据编号位置
     */
    private static final int TOKEN_TICKET_SIGNATURE_VALUE_INDEX = 2;

    /**
     * 身份标识
     */
    private CertificateIdentity certificateIdentity;

    /**
     * 签名
     */
    private String signature;

    /**
     * 配置信息
     */
    private CertificateAuthorizationOptions options;

    /**
     * 私有构造函数
     */
    private CertificateTicket() {
    }


    /**
     * 令牌票据源
     *
     * @return 返回拼接结构
     */
    protected String getTicketSource() {
        String source = JsonMapper.INSTANCE.toJson(certificateIdentity);

        try {
            return RSAUtils.encrypt(source, options.getPublicKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new EncryptionErrorException();
        }
    }

    /**
     * 解密数据
     *
     * @return 解密后的数据
     */
    protected static String decryptTicketSource(String cryptString, CertificateAuthorizationOptions options) {
        try {
            return RSAUtils.decrypt(cryptString, options.getPrivateKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new EncryptionErrorException();
        }
    }

    /**
     * 生成签名
     *
     * @return 返回签名字符串
     */
    protected String generateSignature(String applicationName) {
        String source = MessageFormat.format("{0}{3}{1}{3}{2}", applicationName, getTicketSource(), DateTimeUtils.toUnixTimeMilliseconds(certificateIdentity.getIssuedUtc()).toString(), AUTHORIZATION_TICKET_SEPARATOR_CHAR);

        try {
            return StringSecurityUtils.encryptHmacMd5ToBase64(source, applicationName);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            throw new EncryptionErrorException();
        }
    }


    /**
     * 验证票据合法性
     *
     * @return 如果合法则返回true，反之则为false
     */
    public boolean verify(String applicationName) {
        String generateSignatureString = generateSignature(applicationName);

        if (StringUtils.isBlank(signature) || StringUtils.isBlank(generateSignatureString)) {
            return false;
        }
        return StringUtils.equalsIgnoreCase(signature, generateSignatureString);
    }

    /**
     * 从授权书访问票据值构造访问令牌票据实体
     *
     * @param authorizationTicketValue 授权书访问票据值
     * @return 返回访问令牌票据实体
     */
    public static CertificateTicket formAuthorizationTicketValue(String authorizationTicketValue, CertificateAuthorizationOptions options) {
        if (StringUtils.isBlank(authorizationTicketValue)) {
            return null;
        }
        String[] splitValue = StringUtils.split(authorizationTicketValue, AUTHORIZATION_TICKET_SEPARATOR_CHAR);

        //长度不对时丢弃
        if (splitValue.length != SEPARATOR_LENGTH) {
            return null;
        }

        String tokenTicketSourceString = decryptTicketSource(splitValue[AUTHORIZATION_TICKET_VALUE_INDEX], options);
        CertificateIdentity certificateIdentity = JsonMapper.INSTANCE.fromJson(tokenTicketSourceString, CertificateIdentity.class);

        if (ObjectUtils.isEmpty(certificateIdentity)) {
            return null;
        }

        CertificateTicket customizeTokenTicket = new CertificateTicket();
        customizeTokenTicket.setCertificateIdentity(certificateIdentity);
        customizeTokenTicket.setSignature(splitValue[TOKEN_TICKET_SIGNATURE_VALUE_INDEX]);
        customizeTokenTicket.setOptions(options);
        if (customizeTokenTicket.verify(Base64Utils.decode(splitValue[AUTHORIZATION_TICKET_NAME_VALUE_INDEX]))) {
            return customizeTokenTicket;
        }

        return null;
    }

    /**
     * 从授权书访问票据值构造访问令牌票据实体
     *
     * @param authorizationTicketValue 授权书访问票据值
     * @return 返回访问令牌票据实体
     */
    public static CertificateTicket formAuthorizationIdentity(AuthorizationIdentity authorizationTicketValue, CertificateAuthorizationOptions options) {
        if (ObjectUtils.isEmpty(authorizationTicketValue)) {
            return null;
        }
        return formAuthorizationTicketValue(authorizationTicketValue.getTicket(),options);
    }

    /**
     * 构造新对象
     *
     * @param identity 身份标识
     * @return 返回访问令牌票据实体
     */
    public static CertificateTicket newInstance(Serializable identity, Collection<AuthorizationIdentityRole> roles, Object payload, CertificateAuthorizationOptions options,LocalDateTime issuedUtc) {
        CertificateIdentity certificateIdentity = CertificateIdentity.builder()
            .identity(identity)
            .payload(payload)
            .roles(roles)
            .issuedUtc(issuedUtc)
            .build();
        CertificateTicket customizeTokenTicket = new CertificateTicket();
        customizeTokenTicket.setCertificateIdentity(certificateIdentity);
        String signatureString = customizeTokenTicket.generateSignature(options.getApplicationName());
        customizeTokenTicket.setSignature(signatureString);
        customizeTokenTicket.setOptions(options);
        return customizeTokenTicket;
    }

    /**
     * 返回授权书访问票据值
     *
     * @return 返回授权书访问票据值
     */
    public String toAuthorizationTicketValue() {
        return MessageFormat.format("{0}{3}{1}{3}{2}", Base64Utils.encode(options.getApplicationName()), getTicketSource(), signature, AUTHORIZATION_TICKET_SEPARATOR_CHAR);
    }

    /**
     * 构造授权书身份标识
     *
     * @return 返回授权书身份标识
     */
    public AuthorizationIdentity toAuthorizationIdentity() {
        return AuthorizationIdentity.builder()
            .ticket(toAuthorizationTicketValue())
            .identity(certificateIdentity.getIdentity())
            .payload(certificateIdentity.getPayload())
            .roles(certificateIdentity.getRoles())
            .build();
    }
}
