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

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * 字符串加密解密助手类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class StringSecurityUtils {
    /**
     * 静态工具类不应该被实例化
     */
    private StringSecurityUtils() {
        throw new IllegalStateException("Utility class");
    }
    //region MD5

    /**
     * MD5加密
     *
     * @param data 待加密的数据
     * @return 返回MD5加密后二进制数组
     */
    public static byte[] encryptMd5(final String data) {
        return DigestUtils.md5(data);
    }

    /// <summary>
    /// MD5加密
    /// </summary>
    /// <param name="data">待加密的数据</param>
    /// <returns>返回MD5加密后的十六进制编码字符串</returns>

    /**
     * MD5加密并返回HEX格式字符串
     *
     * @param data
     * @return 返回MD5加密后的十六进制编码字符串
     */
    public static String encryptMd5ToHex(final String data) {
        return DigestUtils.md5Hex(data);
    }

    /**
     * MD5加密并返回Base64编码的字符串
     *
     * @param data 待加密的数据
     * @return 返回MD5加密后的Base64编码字符串
     */
    public static String encryptMd5ToBase64(final String data) {
        byte[] hashBuff = encryptMd5(data);
        return Base64.encodeBase64String(hashBuff);
    }

    /**
     * MD5加密并返回Url安全的Base64编码字符串
     *
     * @param data 待加密的数据
     * @return 返回Url安全的Base64编码字符串
     */
    public static String encryptMd5ToSafeBase64(final String data) {
        byte[] hashBuff = encryptMd5(data);
        return Base64.encodeBase64URLSafeString(hashBuff);
    }
    //endregion

    //region HmacMD5

    /**
     * HmacMD5
     */
    public static final String SECRET_KEY_HMACMD5 = "HmacMD5";


    /**
     * HmacMD5加密
     * <p>
     * data:使用UTF-8编码格式转换为Bytes;
     * </p>
     *
     * @param data 待加密的数据
     * @param key  加密的密钥
     * @return 返回HmacMD5加密后的二进制数组
     * @throws Exception
     */
    public static byte[] encryptHmacMd5(String data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
        SecretKey secretKey = new SecretKeySpec(key, SECRET_KEY_HMACMD5);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * HmacMD5加密
     * <p>
     * key:使用UTF-8编码格式转换为Bytes;
     * </p>
     *
     * @param data 待加密的数据
     * @param key  加密的密钥
     * @return 返回HmacMD5加密后的十六进制字符串
     * @throws Exception
     */
    public static String encryptHmacMd5ToHex(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] hashBuff = encryptHmacMd5(data, key.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(hashBuff);
    }

    /**
     * HmacMD5加密并输出Base64编码的字符串
     *
     * @param data 待加密的数据
     * @param key  加密的密钥
     * @return Base64编码的字符串
     * @throws Exception
     */
    public static String encryptHmacMd5ToBase64(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] hashBuff = encryptHmacMd5(data, key.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(hashBuff);
    }

    /**
     * HmacMD5加密并输出url安全的Base64编码的字符串
     *
     * @param data 待加密的数据
     * @param key  加密的密钥
     * @return
     * @throws Exception
     */
    public static String encryptHmacMd5ToSafeBase64(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] hashBuff = encryptHmacMd5(data, key.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64URLSafeString(hashBuff);
    }

    //endregion

    //region SHA1

    /**
     * SHA1加密
     *
     * @param data 待加密的数据
     * @return 返回SHA1加密后的字节数组
     */
    public static byte[] encryptSha1(final String data) {
        return DigestUtils.sha1(data);
    }

    /**
     * SHA1加密
     *
     * @param data 待加密的数据
     * @return 返回SHA1加密后的字节数组
     */
    public static String encryptSha1ToBase64(final String data) {
        byte[] hashBuff = encryptSha1(data);
        return Base64.encodeBase64String(hashBuff);
    }

    /**
     * SHA1加密
     *
     * @param data 待加密的数据
     * @return 返回SHA1加密后的字节数组
     */
    public static String encryptSha1ToSafeBase64(final String data) {
        byte[] hashBuff = encryptSha1(data);
        return Base64.encodeBase64URLSafeString(hashBuff);
    }

    /**
     * SHA1加密
     *
     * @param data 待加密的数据
     * @return 返回SHA1加密后的字节数组
     */
    public static String encryptSha1ToHex(final String data) {
        return DigestUtils.sha1Hex(data);
    }

    //endregion

    //region HmacSha1

    /**
     * HmacSHA1
     */
    public static final String SECRET_KEY_HMACSHA1 = "HmacSHA1";

    /**
     * HmacSHA1加密
     *
     * @param data 待加密的数据
     * @param key  加密的密钥
     * @return 返回HmacSHA1加密后的二进制数组
     * @throws Exception
     */
    public static byte[] encryptHmacSha1(final String data, final byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
        SecretKey secretKey = new SecretKeySpec(key, SECRET_KEY_HMACSHA1);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * HmacSHA1加密并返回十六进制编码字符串
     *
     * @param data 待加密的数据
     * @param key  加密的密钥
     * @return 返回HmacSHA1加密后的二进制数组
     */
    public static String encryptHmacSha1ToHex(final String data, final String key) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] hashBuff = encryptHmacSha1(data, key.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(hashBuff);
    }

    /**
     * HmacSHA1加密并返回Base64编码字符串
     *
     * @param data 待加密的数据
     * @param key  加密的密钥
     * @return 返回HmacSHA1加密后的Base64编码字符串
     * @throws Exception
     */
    public static String encryptHmacSha1ToBase64(final String data, final String key) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] hashBuff = encryptHmacSha1(data, key.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(hashBuff);
    }

    /**
     * HmacSHA1加密并返回Url安全Base64编码字符串
     *
     * @param data 待加密的数据
     * @param key  加密的密钥
     * @return 返回HmacSHA1加密后的Base64编码字符串
     * @throws Exception
     */
    public static String encryptHmacSha1ToSafeBase64(final String data, final String key) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] hashBuff = encryptHmacSha1(data, key.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64URLSafeString(hashBuff);
    }

    //endregion

    //region SHA256

    /**
     * HA256加密
     *
     * @param data 待加密的数据
     * @return 返回HA256加密后的字节数组
     */
    public static byte[] encryptSha256(final String data) {
        return DigestUtils.sha256(data);
    }

    /**
     * HA256加密
     *
     * @param data 待加密的数据
     * @return 返回SHA256加密后的Base64编码密文
     */
    public static String encryptSha256ToBase64(final String data) {
        byte[] hashBuff = encryptSha256(data);
        return Base64.encodeBase64String(hashBuff);
    }

    /**
     * HA256加密
     *
     * @param data 待加密的数据
     * @return 返回SHA256加密后的Base64编码密文
     */
    public static String encryptSha256ToSafeBase64(final String data) {
        byte[] hashBuff = encryptSha256(data);
        return Base64.encodeBase64URLSafeString(hashBuff);
    }

    /**
     * HA256加密
     *
     * @param data 待加密的数据
     * @return 返回SHA256加密后的十六进制字符串
     */
    public static String encryptSha256ToHex(final String data) {
        return DigestUtils.sha256Hex(data);
    }

    //endregion

    //region HmacSha256

    /**
     * HmacSHA256
     */
    public static final String SECRET_KEY_HMACSHA256 = "HmacSHA256";

    /**
     * HmacSHA256加密
     *
     * @param data 待加密的数据
     * @param key  加密的密钥
     * @return 返回HmacSHA256加密后的二进制数组
     * @throws Exception
     */
    public static byte[] encryptHmacSha256(final String data, final byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
        SecretKey secretKey = new SecretKeySpec(key, SECRET_KEY_HMACSHA256);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * HmacSHA256加密并返回十六进制编码字符串
     *
     * @param data 待加密的数据
     * @param key  加密的密钥
     * @return 返回HmacSHA256加密后的二进制数组
     */
    public static String encryptHmacSha256ToHex(final String data, final String key) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] hashBuff = encryptHmacSha256(data, key.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(hashBuff);
    }

    /**
     * HmacSHA256加密并返回Base64编码字符串
     *
     * @param data 待加密的数据
     * @param key  加密的密钥
     * @return 返回HmacSHA256加密后的Base64编码字符串
     * @throws Exception
     */
    public static String encryptHmacSha256ToBase64(final String data, final String key) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] hashBuff = encryptHmacSha256(data, key.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(hashBuff);
    }

    /**
     * HmacSHA256加密并返回Url安全Base64编码字符串
     *
     * @param data 待加密的数据
     * @param key  加密的密钥
     * @return 返回HmacSHA256加密后的Base64编码字符串
     * @throws Exception
     */
    public static String encryptHmacSha256ToSafeBase64(final String data, final String key) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] hashBuff = encryptHmacSha256(data, key.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64URLSafeString(hashBuff);
    }

    //endregion

    //region SHA512

    /**
     * HA512加密
     *
     * @param data 待加密的数据
     * @return 返回SHA512加密后的字节数组
     */
    public static byte[] encryptSha512(final String data) {
        return DigestUtils.sha512(data);
    }

    /**
     * HA512加密
     *
     * @param data 待加密的数据
     * @return 返回SHA512加密后的Base64编码密文
     */
    public static String encryptSha512ToBase64(final String data) {
        byte[] hashBuff = encryptSha512(data);
        return Base64.encodeBase64String(hashBuff);
    }

    /**
     * HA512加密
     *
     * @param data 待加密的数据
     * @return 返回SHA512加密后的Base64编码密文
     */
    public static String encryptSha512ToSafeBase64(final String data) {
        byte[] hashBuff = encryptSha512(data);
        return Base64.encodeBase64URLSafeString(hashBuff);
    }

    /**
     * HA512加密
     *
     * @param data 待加密的数据
     * @return 返回SHA512加密后的十六进制字符串
     */
    public static String encryptSha512ToHex(final String data) {
        return DigestUtils.sha512Hex(data);
    }

    //endregion

    //region HmacSha512

    /**
     * HmacSHA256
     */
    public static final String SECRET_KEY_HMACSHA512 = "HmacSHA512";

    /**
     * HmacSHA512加密
     *
     * @param data 待加密的数据
     * @param key  加密的密钥
     * @return 返回HmacSHA512加密后的二进制数组
     * @throws Exception
     */
    public static byte[] encryptHmacSha512(final String data, final byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
        SecretKey secretKey = new SecretKeySpec(key, SECRET_KEY_HMACSHA512);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * HmacSHA512加密并返回十六进制编码字符串
     *
     * @param data 待加密的数据
     * @param key  加密的密钥
     * @return 返回HmacSHA512加密后的二进制数组
     */
    public static String encryptHmacSha512ToHex(final String data, final String key) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] hashBuff = encryptHmacSha512(data, key.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(hashBuff);
    }

    /**
     * HmacSHA512加密并返回Base64编码字符串
     *
     * @param data 待加密的数据
     * @param key  加密的密钥
     * @return 返回HmacSHA512加密后的Base64编码字符串
     * @throws Exception
     */
    public static String encryptHmacSha512ToBase64(final String data, final String key) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] hashBuff = encryptHmacSha512(data, key.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(hashBuff);
    }

    /**
     * HmacSHA512加密并返回Url安全Base64编码字符串
     *
     * @param data 待加密的数据
     * @param key  加密的密钥
     * @return 返回HmacSHA512加密后的Base64编码字符串
     * @throws Exception
     */
    public static String encryptHmacSha512ToSafeBase64(final String data, final String key) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] hashBuff = encryptHmacSha512(data, key.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64URLSafeString(hashBuff);
    }

    //endregion

    //region AES

    /**
     * 获取AES加密算法全称
     *
     * @param mode    密码模式
     * @param padding 补码方式
     * @return 加密算法全称
     */
    private static String getAesAlgorithmName(CipherMode mode, PaddingMode padding) {
        return "AES/" + mode.getName() + "/" + padding.getName();
    }

    /**
     * AES-128算法KEY最小长度
     */
    public static final Integer AES_KEY_MIN_LENGTH = 16;

    /**
     * AES-128算法默认向量
     */
    public static final String AES_DEFAUL_IV = "0000000000000000";

    /**
     * AES加密
     *
     * @param data    待加密的明文
     * @param key     密钥
     * @param iv      向量
     * @param mode    密码模式
     * @param padding 补码形式
     * @return 返回AES加密后的字节数组
     */
    public static byte[] encryptAes(final String data, final byte[] key, final byte[] iv, CipherMode mode, PaddingMode padding) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        if (ObjectUtils.isEmpty(key)) {
            throw new IllegalArgumentException("Key IsNot Null");
        }
        if (ObjectUtils.isEmpty(iv)) {
            throw new IllegalArgumentException("IV IsNot Null");
        }
        Security.addProvider(new BouncyCastleProvider());
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        String aesAlgorithmName = getAesAlgorithmName(mode, padding);
        Cipher cipher = Cipher.getInstance(aesAlgorithmName);
        if (mode != CipherMode.ECB) {
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        }
        //"算法/模式/补码方式"
        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * AES加密并返回Base64编码的字符串
     *
     * @param data    待加密的明文
     * @param key     密钥
     * @param iv      向量
     * @param mode    密码模式
     * @param padding 补码形式
     * @return 返回AES加密后的Base64编码的字符串
     * @throws Exception
     */
    public static String encryptAesToBase64(final String data, final byte[] key, final byte[] iv, CipherMode mode, PaddingMode padding) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        byte[] hashBuff = encryptAes(data, key, iv, mode, padding);
        return Base64.encodeBase64String(hashBuff);
    }

    /**
     * AES加密并返回Base64编码的字符串【Cipher：ECB，Padding：PKCS7,IV:000000000000000】
     *
     * @param data 待加密的明文
     * @param key  加密公钥
     * @return 返回AES加密后的Base64编码的密文
     * @throws Exception
     */
    public static String encryptAesToBase64(final String data, final String key) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return encryptAesToBase64(data, key, AES_DEFAUL_IV, CipherMode.ECB, PaddingMode.PKCS7);
    }

    /**
     * AES加密并返回Base64编码的字符串
     *
     * @param data
     * @param data    待加密的明文
     * @param key     密钥
     * @param iv      向量
     * @param mode    密码模式
     * @param padding 补码形式
     * @return
     * @throws Exception
     */
    public static String encryptAesToBase64(final String data, final String key, final String iv, CipherMode mode, PaddingMode padding) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

        if (key.length() < AES_KEY_MIN_LENGTH) {
            return null;
        }
        if (iv.length() < AES_KEY_MIN_LENGTH) {
            return null;
        }
        return encryptAesToBase64(data, key.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8), mode, padding);
    }

    /**
     * AES加密并返回URL安全的Base64编码的字符串
     *
     * @param data    待加密的明文
     * @param key     密钥
     * @param iv      向量
     * @param mode    密码模式
     * @param padding 补码形式
     * @return 返回AES加密后的URL安全的Base64编码的字符串
     * @throws Exception
     */
    public static String encryptAesToSafeBase64(final String data, final byte[] key, final byte[] iv, CipherMode mode, PaddingMode padding) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        byte[] hashBuff = encryptAes(data, key, iv, mode, padding);
        return Base64.encodeBase64URLSafeString(hashBuff);
    }

    /**
     * AES加密并返回URL安全的Base64编码的字符串【Cipher：ECB，Padding：PKCS7,IV:000000000000000】
     *
     * @param data 待加密的明文
     * @param key  加密公钥
     * @return 返回AES加密后的Base64编码的密文
     * @throws Exception
     */
    public static String encryptAesToSafeBase64(final String data, final String key) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return encryptAesToSafeBase64(data, key, AES_DEFAUL_IV, CipherMode.ECB, PaddingMode.PKCS7);
    }

    /**
     * AES加密并返回URL安全的Base64编码的字符串
     *
     * @param data
     * @param data    待加密的明文
     * @param key     密钥
     * @param iv      向量
     * @param mode    密码模式
     * @param padding 补码形式
     * @return
     * @throws Exception
     */
    public static String encryptAesToSafeBase64(final String data, final String key, final String iv, CipherMode mode, PaddingMode padding) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        if (key.length() < AES_KEY_MIN_LENGTH) {
            return null;
        }
        if (iv.length() < AES_KEY_MIN_LENGTH) {
            return null;
        }
        return encryptAesToSafeBase64(data, key.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8), mode, padding);
    }


    /**
     * AES加密并返回十六进制字符串
     *
     * @param data    待加密的明文
     * @param key     密钥
     * @param iv      向量
     * @param mode    密码模式
     * @param padding 补码形式
     * @return 返回AES加密后的十六进制字符串
     * @throws Exception
     */
    public static String encryptAesToHex(final String data, final byte[] key, final byte[] iv, CipherMode mode, PaddingMode padding) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        byte[] hashBuff = encryptAes(data, key, iv, mode, padding);
        return Hex.encodeHexString(hashBuff);
    }

    /**
     * AES加密并返回十六进制字符串【Cipher：ECB，Padding：PKCS7,IV:000000000000000】
     *
     * @param data 待加密的明文
     * @param key  加密公钥
     * @return 返回AES加密后的十六进制字符串
     * @throws Exception
     */
    public static String encryptAesToHex(final String data, final String key) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return encryptAesToHex(data, key, AES_DEFAUL_IV, CipherMode.ECB, PaddingMode.PKCS7);
    }

    /**
     * AES加密并返回十六进制字符串
     *
     * @param data    待加密的明文
     * @param key     密钥
     * @param iv      向量
     * @param mode    密码模式
     * @param padding 补码形式
     * @return 返回十六进制字符串
     * @throws Exception
     */
    public static String encryptAesToHex(final String data, final String key, final String iv, CipherMode mode, PaddingMode padding) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        if (key.length() < AES_KEY_MIN_LENGTH) {
            return null;
        }
        if (iv.length() < AES_KEY_MIN_LENGTH) {
            return null;
        }
        return encryptAesToHex(data, key.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8), mode, padding);
    }

    /**
     * AES解密
     *
     * @param data    待解密的密文
     * @param key     密钥
     * @param iv      向量
     * @param mode    密码模式
     * @param padding 补码形式
     * @return 返回解密后的字节数组
     */
    public static byte[] decryptAes(final String data, final byte[] key, final byte[] iv, CipherMode mode, PaddingMode padding) throws DecoderException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        if (ObjectUtils.isEmpty(key)) {
            throw new IllegalArgumentException("Key IsNot Null");
        }
        if (ObjectUtils.isEmpty(iv)) {
            throw new IllegalArgumentException("IV IsNot Null");
        }

        byte[] dataBytes;
        if (Base64.isBase64(data)) {
            dataBytes = Base64.decodeBase64(data);
        } else {
            dataBytes = Hex.decodeHex(data);
        }
        Security.addProvider(new BouncyCastleProvider());
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        //"算法/模式/补码方式"
        String aesAlgorithmName = getAesAlgorithmName(mode, padding);
        Cipher cipher = Cipher.getInstance(aesAlgorithmName);

        if (mode != CipherMode.ECB) {
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
        }
        return cipher.doFinal(dataBytes);
    }

    /**
     * AES解密返回UTF8编码字符串
     *
     * @param data    待解密的密文
     * @param key     密钥
     * @param iv      向量
     * @param mode    密码模式
     * @param padding 补码形式
     * @return 返回解密后的原文
     * @throws Exception
     */
    public static String decryptAesToString(final String data, final byte[] key, final byte[] iv, CipherMode
        mode, PaddingMode padding) throws
        NoSuchPaddingException, InvalidKeyException, DecoderException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return StringUtils.newStringUtf8(decryptAes(data, key, iv, mode, padding));
    }

    /**
     * AES解密返回UTF8编码字符串
     *
     * @param data    待解密的密文
     * @param key     密钥
     * @param iv      向量
     * @param mode    密码模式
     * @param padding 补码形式
     * @return 返回解密后的原文
     * @throws Exception
     */
    public static String decryptAesToString(final String data, final String key, final String iv, CipherMode
        mode, PaddingMode padding) throws
        NoSuchPaddingException, InvalidKeyException, DecoderException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return StringUtils.newStringUtf8(decryptAes(data, key.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8), mode, padding));
    }

    /**
     * AES解密返回UTF8编码字符串
     *
     * @param data    待解密的密文
     * @param key     密钥
     * @param mode    密码模式
     * @param padding 补码形式
     * @return 返回解密后的原文
     * @throws Exception
     */
    public static String decryptAesToString(final String data, final String key, CipherMode mode, PaddingMode
        padding) throws
        NoSuchPaddingException, InvalidAlgorithmParameterException, DecoderException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return decryptAesToString(data, key, AES_DEFAUL_IV, mode, padding);
    }

    /**
     * AES解密返回UTF8编码字符串
     *
     * @param data 待解密的密文
     * @param key  密钥
     * @return 返回解密后的原文
     * @throws Exception
     */
    public static String decryptAesToString(final String data, final String key) throws
        NoSuchPaddingException, InvalidKeyException, DecoderException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return decryptAesToString(data, key, CipherMode.ECB, PaddingMode.PKCS7);
    }
    //endregion
}
