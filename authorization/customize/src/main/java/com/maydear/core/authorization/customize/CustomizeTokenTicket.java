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
package com.maydear.core.authorization.customize;

import com.maydear.core.authorization.AuthorizationIdentity;
import com.maydear.core.authorization.AuthorizationIdentityRole;
import com.maydear.core.framework.exception.EncryptionErrorException;
import com.maydear.core.framework.util.DateTimeUtils;
import com.maydear.core.framework.util.StringSecurityUtils;
import com.maydear.core.framework.util.UUIDUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
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
public class CustomizeTokenTicket {

    /**
     * 授权书访问票据分隔符
     */
    private static final char AUTHORIZATION_TICKET_SEPARATOR_CHAR = '.';

    /**
     * 票据值分隔符
     */
    private static final char TICKET_VALUE_SEPARATOR_CHAR = '|';
    /**
     * 格式长度
     */
    private static final int SEPARATOR_LENGTH = 3;

    /**
     * 签名值的位置
     */
    private static final int SIGNATURE_VALUE_INDEX = 1;

    /**
     * 票据编号的位置
     */
    private static final int TICKET_ID_VALUE_INDEX = 2;

    /**
     * 令牌数据的票据编号位置
     */
    private static final int TOKEN_TICKET_TICKET_ID_VALUE_INDEX = 1;

    /**
     * 令牌数据的发行时间位置
     */
    private static final int TOKEN_TICKET_ISSUED_UTC_VALUE_INDEX = 2;

    /**
     * 身份标识
     */
    private String identity;

    /**
     * 签名
     */
    private String signature;

    /**
     * 票据id
     */
    private String ticketId;

    /**
     * 发行时间
     */
    private LocalDateTime issuedUtc;

    /**
     * 私有构造函数
     */
    private CustomizeTokenTicket() {
    }

    /**
     * 令牌票据源
     *
     * @return 返回拼接结构
     */
    protected String getTicketSource() {
        return MessageFormat.format("{0}{3}{1}{3}{2}", identity, ticketId, DateTimeUtils.toUnixTimeMilliseconds(issuedUtc).toString(), TICKET_VALUE_SEPARATOR_CHAR);
    }

    /**
     * 生成授权书访问票据
     *
     * @return 返回票据字符串
     */
    public String generateAuthorizationTicketValue() {
        String generateSignatureString = generateSignature();
        String tokenTicket;
        try {
            tokenTicket = StringSecurityUtils.encryptAesToBase64(getTicketSource(), ticketId);
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException e) {
            log.error(e.getMessage());
            throw new EncryptionErrorException();
        }
        return MessageFormat.format("{0}{3}{1}{3}{2}", tokenTicket, generateSignatureString, ticketId, AUTHORIZATION_TICKET_SEPARATOR_CHAR);
    }

    /**
     * 生成签名
     *
     * @param ticketId 票据id
     * @return 返回签名字符串
     */
    public String generateSignature(String ticketId) {
        try {
            return StringSecurityUtils.encryptHmacMd5ToBase64(getTicketSource(), ticketId);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            return StringUtils.EMPTY;
        }
    }

    /**
     * 生成签名
     *
     * @return 返回签名字符串
     */
    private String generateSignature() {
        if (StringUtils.isNotBlank(ticketId)) {
            signature = generateSignature(ticketId);
            return signature;
        }
        return StringUtils.EMPTY;
    }


    /**
     * 验证票据合法性
     *
     * @return 如果合法则返回true，反之则为false
     */
    public boolean verify() {
        String generateSignatureString = generateSignature();

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
    public static CustomizeTokenTicket formAuthorizationTicketValue(String authorizationTicketValue) {
        String[] splitValue = StringUtils.split(authorizationTicketValue, AUTHORIZATION_TICKET_SEPARATOR_CHAR);

        if (splitValue.length != SEPARATOR_LENGTH) {
            return null;
        }

        String tokenTicketSourceString;
        try {
            tokenTicketSourceString = StringSecurityUtils.decryptAesToString(splitValue[0], splitValue[2]);
        } catch (DecoderException | NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            log.error(e.getMessage());
            throw new EncryptionErrorException();
        }
        String[] tokenTicketSplitValue = StringUtils.split(tokenTicketSourceString, TICKET_VALUE_SEPARATOR_CHAR);
        if (tokenTicketSplitValue.length != SEPARATOR_LENGTH) {
            return null;
        }

        if (!StringUtils.equalsIgnoreCase(tokenTicketSplitValue[TOKEN_TICKET_TICKET_ID_VALUE_INDEX], splitValue[TICKET_ID_VALUE_INDEX])) {
            return null;
        }

        CustomizeTokenTicket customizeTokenTicket = new CustomizeTokenTicket();
        customizeTokenTicket.setIdentity(tokenTicketSplitValue[0]);
        customizeTokenTicket.setSignature(splitValue[SIGNATURE_VALUE_INDEX]);
        customizeTokenTicket.setTicketId(splitValue[TICKET_ID_VALUE_INDEX]);
        customizeTokenTicket.setIssuedUtc(DateTimeUtils.formUnixTimeMilliseconds(Long.parseLong(tokenTicketSplitValue[TOKEN_TICKET_ISSUED_UTC_VALUE_INDEX])));
        if (customizeTokenTicket.verify()) {
            return customizeTokenTicket;
        }

        return null;
    }

    /**
     * 构造新对象
     *
     * @param identity 身份标识
     * @return 返回访问令牌票据实体
     */
    public static CustomizeTokenTicket newInstance(String identity) {
        String ticketId = UUIDUtils.generateNoUnderline();
        LocalDateTime nowDateTimeUtc = LocalDateTime.now(ZoneOffset.UTC);
        CustomizeTokenTicket customizeTokenTicket = new CustomizeTokenTicket();
        customizeTokenTicket.setIdentity(identity);
        customizeTokenTicket.setTicketId(ticketId);
        customizeTokenTicket.setIssuedUtc(nowDateTimeUtc);
        String signatureString = customizeTokenTicket.generateSignature();
        customizeTokenTicket.setSignature(signatureString);
        return customizeTokenTicket;
    }

    /**
     * 构造授权书身份标识
     *
     * @param roles   授权角色
     * @param payload 载荷
     * @return 返回授权书身份标识
     */
    public AuthorizationIdentity buildAuthorizationIdentity(Collection<AuthorizationIdentityRole> roles, Object payload) {
        return AuthorizationIdentity.builder()
            .ticket(generateAuthorizationTicketValue())
            .identity(identity)
            .payload(payload)
            .roles(roles)
            .build();
    }

    /**
     * 构造授权书身份标识
     *
     * @param roles 授权角色
     * @return 返回授权书身份标识
     */
    public AuthorizationIdentity buildAuthorizationIdentity(Collection<AuthorizationIdentityRole> roles) {
        return buildAuthorizationIdentity(roles, null);
    }

    /**
     * 构造授权书身份标识
     *
     * @return 返回授权书身份标识
     */
    public AuthorizationIdentity buildAuthorizationIdentity() {
        return buildAuthorizationIdentity(null, null);
    }
}
