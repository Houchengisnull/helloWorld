package org.hc.learning.安全.基础.crypto;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;

public class B2DH算法密钥对生成 {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance( "DH" );
		KeyPair kp1 = kpg.genKeyPair();
		KeyPair kp2 = kpg.genKeyPair();
		
		KeyAgreement keyAgree = KeyAgreement.getInstance(kpg.getAlgorithm());
		keyAgree.init(kp2.getPrivate());
		// 执行计划
		keyAgree.doPhase(kp1.getPublic(), true);
		// 生成SecretKey对象
		SecretKey secretKey = keyAgree.generateSecret("DES");
	}
}
