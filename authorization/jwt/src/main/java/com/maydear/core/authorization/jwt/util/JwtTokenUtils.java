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
package com.maydear.core.authorization.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.maydear.core.authorization.exception.AuthorizationFailedException;
import com.maydear.core.authorization.exception.VerificationFailedException;
import com.maydear.core.authorization.jwt.JwtOptions;
import com.maydear.core.authorization.jwt.JwtTicket;
import com.maydear.core.framework.exception.AuthorizedExpiredException;
import com.maydear.core.framework.jackson.mapper.JsonMapper;
import com.maydear.core.framework.util.DateExtensionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * jwt令牌工具类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class JwtTokenUtils {

    /**
     *
     */
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);

    /**
     * 防止静态类被实例化
     */
    private JwtTokenUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 生成 JWT Token 对象
     *
     * @return JWT Token
     */
    public static String encode(JwtTicket jwtTicket, JwtOptions options) {
        return encodeAsString(jwtTicket.getIdentity(), JsonMapper.INSTANCE.toJson(jwtTicket), options);
    }

    /**
     * 生成指定类型的 Token
     *
     * @param jwtId   ID
     * @param subject Subject
     * @return JWT Token
     */
    private static String encodeAsString(String jwtId, String subject, JwtOptions options) {

        // 证书
        String secret = JwtOptions.DEFAULT_SECRET;

        //发行方
        String issuer = JwtOptions.DEFAULT_ISSUER;

        //失效时间(秒)
        long expired = JwtOptions.DEFAULT_EXPIRED;

        //配置数据
        if (ObjectUtils.isNotEmpty(options)) {
            if (ObjectUtils.isNotEmpty(options.getExpired()) && options.getExpired() > 0) {
                expired = options.getExpired();
            }
            if (StringUtils.isNotBlank(options.getIssuer())) {
                issuer = options.getIssuer();
            }
            if (StringUtils.isNotBlank(options.getSecret())) {
                secret = options.getSecret();
            }
        }

        //发行时间(毫秒)
        long issuedMillis = System.currentTimeMillis();

        //到期时间(毫秒)
        long expiredMillis = System.currentTimeMillis() + expired * 1000;

        return JWT.create()
                .withJWTId(jwtId)
                .withSubject(subject)
                .withIssuer(issuer)
                .withIssuedAt(new Date(issuedMillis))
                .withExpiresAt(new Date(expiredMillis))
                .sign(Algorithm.HMAC256(secret));
    }


    /**
     * 验证并解密指定类型的 Token
     *
     * @return 解密后的 JWT Token
     */
    public static JwtTicket decode(String token, JwtOptions options) {
        JwtTicket jwtTicket = null;
        // 证书
        String secret = JwtOptions.DEFAULT_SECRET;
        //配置数据
        if (ObjectUtils.isNotEmpty(options) && StringUtils.isNotBlank(options.getSecret())) {
            secret = options.getSecret();
        }
        try {
            DecodedJWT decoded = JWT.decode(token);
            LocalDateTime issueAt = DateExtensionUtils.dateToLocalDateTime(decoded.getIssuedAt());
            logger.debug("Token issueAt: {}", issueAt);
            LocalDateTime expiredAt = DateExtensionUtils.dateToLocalDateTime(decoded.getExpiresAt());
            logger.debug("Token expiredAt: {}", expiredAt);

            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(decoded);
            String jsonString = decoded.getSubject();
            logger.debug("subject: {}", jsonString);
            jwtTicket = JsonMapper.INSTANCE.fromJson(jsonString, JwtTicket.class);
            return jwtTicket;
        } catch (TokenExpiredException e) {
            throw new AuthorizedExpiredException();
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new AuthorizationFailedException(e.getCause());
        } catch (JWTVerificationException e) {
            throw new VerificationFailedException(e.getCause());
        }
    }
}
