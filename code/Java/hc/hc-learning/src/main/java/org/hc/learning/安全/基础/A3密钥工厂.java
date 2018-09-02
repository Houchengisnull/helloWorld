package org.hc.learning.安全.基础;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class A3密钥工厂 {
	public static void main(String[] args) {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA"); 			// 实例化KeyPairGenerator对象, 并指定RSA算法(经典非对称加密算法)
			keyPairGen.initialize(1024);												// 初始化KeyPairGenerator对象 
			KeyPair keyPair = keyPairGen.generateKeyPair();								// 生成KeyPair对象
			byte[] keyBytes = keyPair.getPrivate().getEncoded();						// 获得私钥密钥字节数组。实际使用过程中该密钥以此种形式保存传递给另一方
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);// 由私钥密钥字节数组获得 密钥规范 KeySpec
			KeyFactory factory = KeyFactory.getInstance("RSA");							// 实例化工厂 
			PrivateKey generatePrivate = factory.generatePrivate(pkcs8EncodedKeySpec);	// 生成私钥
			System.out.println(keyBytes);
			System.out.println(generatePrivate);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}
}
