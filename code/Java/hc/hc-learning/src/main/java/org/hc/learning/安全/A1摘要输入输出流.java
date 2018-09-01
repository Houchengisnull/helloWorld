package org.hc.learning.安全;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class A1摘要输入输出流 {
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		byte[] input = "md5".getBytes();
		// 使用md5算法
		MessageDigest md = MessageDigest.getInstance("md5");
		DigestInputStream dis = new DigestInputStream( new ByteArrayInputStream( input ), md );
		// 流输入
		dis.read(input, 0, input.length );
		// 获得摘要信息
		byte[] output = dis.getMessageDigest().digest();
		System.out.println( output );
		dis.close();
	}
}
