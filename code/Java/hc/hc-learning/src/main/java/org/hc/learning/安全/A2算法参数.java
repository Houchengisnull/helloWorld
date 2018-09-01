package org.hc.learning.安全;

import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;

public class A2算法参数 {
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		// 获得实例
		AlgorithmParameters ap = AlgorithmParameters.getInstance("DES");
		// 使用BigInteger生成参数对象数组
		ap.init(new BigInteger("19050619766489163472469").toByteArray());
		// 获得参数数组
		byte[] b = ap.getEncoded();
		// 打印结果
		System.out.println(ap.getProvider().getInfo() );
		
		ap.getProvider().getServices().forEach( s -> {
			System.out.println( s.getAlgorithm() );
		});
		System.out.println( new BigInteger(b).toString() );
	}
}
