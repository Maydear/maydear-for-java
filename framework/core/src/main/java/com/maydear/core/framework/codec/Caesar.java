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
package com.maydear.core.framework.codec;

/**
 * 凯撒密码实现<br>
 * 算法来自：https://github.com/zhaorenjie110/SymmetricEncryptionAndDecryption
 *
 * @author kelvin.liang
 */
public class Caesar {

    /**
     * 静态类不应该被实例化
     */
    private Caesar(){
        throw new IllegalStateException("Utility class");
    }

    /**
     * 26个字母表
     */
	public static final String TABLE = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

	/**
	 * 传入明文，加密得到密文
	 *
	 * @param message 加密的消息
	 * @param offset  偏移量
	 * @return 加密后的内容
	 */
	public static String encode(String message, int offset) {
		final int len = message.length();
		final char[] plain = message.toCharArray();
		char c;
		for (int i = 0; i < len; i++) {
			c = message.charAt(i);
			if (!Character.isLetter(c)) {
				continue;
			}
			plain[i] = encodeChar(c, offset);
		}
		return new String(plain);
	}

	/**
	 * 传入明文解密到密文
	 *
	 * @param cipherText 密文
	 * @param offset     偏移量
	 * @return 解密后的内容
	 */
	public static String decode(String cipherText, int offset) {
		final int len = cipherText.length();
		final char[] plain = cipherText.toCharArray();
		char c;
		for (int i = 0; i < len; i++) {
			c = cipherText.charAt(i);
			if (!Character.isLetter(c)) {
				continue;
			}
			plain[i] = decodeChar(c, offset);
		}
		return new String(plain);
	}

	// region Private method

	/**
	 * 加密轮盘
	 *
	 * @param c      被加密字符
	 * @param offset 偏移量
	 * @return 加密后的字符
	 */
	private static char encodeChar(char c, int offset) {
		int position = (TABLE.indexOf(c) + offset) % 52;
		return TABLE.charAt(position);

	}

	/**
	 * 解密轮盘
	 *
	 * @param c 字符
	 * @return 解密后的字符
	 */
	private static char decodeChar(char c, int offset) {
		int position = (TABLE.indexOf(c) - offset) % 52;
		if (position < 0) {
			position += 52;
		}
		return TABLE.charAt(position);
	}
	// endregion
}
