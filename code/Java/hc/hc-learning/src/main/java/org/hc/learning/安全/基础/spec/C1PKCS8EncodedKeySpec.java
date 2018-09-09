package org.hc.learning.安全.基础.spec;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class C1PKCS8EncodedKeySpec {
	@SuppressWarnings({ "unused" })
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");
		keygen.initialize( 1024 );
		KeyPair keys = keygen.generateKeyPair();
		
		// 获得公钥密钥字节数组
		byte[] publicKeyBytes = keys.getPublic().getEncoded();
		// 实例化X509EncodedKeySpec对象
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec( publicKeyBytes );
		// 实例化KeyFactory 并指定DSA算法
		KeyFactory keyFactory = KeyFactory.getInstance( "DSA" );
		// 获得PublicKey对象
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
	}
}
