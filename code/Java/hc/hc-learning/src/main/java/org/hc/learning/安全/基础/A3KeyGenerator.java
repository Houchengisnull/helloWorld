package org.hc.learning.安全.基础;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class A3KeyGenerator {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		kg.init(56);
		SecretKey key = kg.generateKey();
		byte[] encoded = key.getEncoded();
		System.out.println( new BigInteger( encoded ) );
	}
}
