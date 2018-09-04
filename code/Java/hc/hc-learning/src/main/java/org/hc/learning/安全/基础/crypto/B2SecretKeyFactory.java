package org.hc.learning.安全.基础.crypto;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class B2SecretKeyFactory {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		SecretKey secretKey = keyGenerator.generateKey();
		byte[] key = secretKey.getEncoded();
		
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey2 = keyFactory.generateSecret(dks);
		byte[] key2 = secretKey2.getEncoded();
		
		System.out.println( "key : " + new BigInteger(key) );
		System.out.println( "key2: " + new BigInteger(key2) );
	}
}
