package org.hc.learning.安全.基础;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class A4SecureRandom {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		// 实例化SecureRandom对象
		SecureRandom secureRandom = new SecureRandom();
		// 实例化KeyGenerator对象
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		// 初始化KeyGenerator对象
		kg.init(secureRandom);
		// 生成SecretKey对象
		@SuppressWarnings("unused")
		SecretKey secretKey = kg.generateKey();
	}
}
