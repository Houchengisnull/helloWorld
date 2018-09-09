package org.hc.learning.安全.基础.bouncy;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class F1MD4 {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		byte[] data = "Helloworld".getBytes();
		MessageDigest md = MessageDigest.getInstance("MD4");
		md.update(data);
		byte[] digest = md.digest();
		System.out.println( new String(digest));
	}
}
