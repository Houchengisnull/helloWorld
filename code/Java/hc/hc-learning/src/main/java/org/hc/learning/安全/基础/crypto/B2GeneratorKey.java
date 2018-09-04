package org.hc.learning.安全.基础.crypto;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class B2GeneratorKey {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance( "HmacMD5" );
		@SuppressWarnings("unused")
		SecretKey secretKey = keyGenerator.generateKey();
	}
}
