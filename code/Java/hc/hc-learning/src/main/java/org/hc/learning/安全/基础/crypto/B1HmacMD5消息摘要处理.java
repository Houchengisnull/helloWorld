package org.hc.learning.安全.基础.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

public class B1HmacMD5消息摘要处理 {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
		// 待做安全消息摘要的原始信息
		byte[] input = "Mac".getBytes();
		// 初始化KeyGenerator对象，使用HmacMD5算法
		KeyGenerator keyGenerator = KeyGenerator.getInstance( "HmacMD5" );
		// 构建SecretKey对象
		SecretKey secretKey = keyGenerator.generateKey();
		// 构建Mac对象
		Mac mac = Mac.getInstance( secretKey.getAlgorithm() );
		// 初始化
		mac.init(secretKey);
		// 获得经过安全消息摘要后的信息
		byte[] doFinal = mac.doFinal( input );
		System.out.println( doFinal );
	}
}
