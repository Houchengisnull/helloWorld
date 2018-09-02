package org.hc.learning.安全.基础;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

/**
 * 数字签名处理
 */
public class A4Signature {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		// 待做数字签名的原始信息
		byte[] data = "Data Signature".getBytes();
		// 实例化KeyPairGenerator对象 指定DSA算法
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance( "DSA" );
		// 初始化KeyPairGenerator对象
		keyPairGen.initialize(1024);
		// 生成KeyPair对象
		KeyPair keyPair = keyPairGen.generateKeyPair();
		
		// 实例化Signature对象
		Signature signature = Signature.getInstance( keyPairGen.getAlgorithm() );
		// 初始化用于签名操作的Signature对象
		signature.initSign(keyPair.getPrivate());
		// 更新
		signature.update( data );
		// 获得签名，即字节数组 sign
		byte[] sign = signature.sign();
		
		System.out.println( "私钥签名对象:" + sign );
		System.out.println( "私钥 : " + new BigInteger( sign ));
		
		// 完成验证
		signature.initVerify( keyPair.getPublic() );
		// 更新
		signature.update( data );
		// 获得验证结果
		boolean result = signature.verify(sign);
		System.out.println( "公钥验证结果: " + result );
	}
}
