package org.hc.learning.安全.基础;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class A0消息摘要算法 {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		// 待做消息摘要算法的原始信息
		byte[] input = "sha".getBytes();
		// 初始化MessageDigest对象，使用sha算法
		MessageDigest sha = MessageDigest.getInstance("sha");
		// 更新消息摘要
		sha.update(input);
		// 获取消息摘要结果
		byte[] output = sha.digest();
		System.out.println(output);
		
	}
}
