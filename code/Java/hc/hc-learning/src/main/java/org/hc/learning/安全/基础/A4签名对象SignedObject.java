package org.hc.learning.安全.基础;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SignedObject;

/**
 * Https 中数字签名 为 网站信息与私钥 Hash加密后再用CA私钥加密 的结果
 * @author Administrator
 *
 */
public class A4签名对象SignedObject {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		// 数字签名原始信息
		byte[] data = "Data Source".getBytes();
		// 实例化KeyPairGenerator对象 并指定DSA算法
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
		// 初始化KeyPairGenerator对象
		keyPairGen.initialize( 1024 );
		// 生成KeyPair对象
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// 实例化Signature对象
		Signature signature = Signature.getInstance( keyPairGen.getAlgorithm() );
		
		SignedObject s = new SignedObject(data, keyPair.getPrivate(), signature );
		// 获得签名值
		//byte[] sign = s.getSignature();
		boolean result = s.verify(keyPair.getPublic(), signature);
		System.out.println( "验证结果:" + result );
	}
}
