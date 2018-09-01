package org.hc.learning.安全;

import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;

public class A2算法参数生成器 {
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		
		// 获得实例
		//AlgorithmParameterGenerator apg = AlgorithmParameterGenerator.getInstance("DES");
		//AlgorithmParameterGenerator apg = AlgorithmParameterGenerator.getInstance("DES", Security.getProvider("SunJCE") );
		AlgorithmParameterGenerator apg = AlgorithmParameterGenerator.getInstance("DiffieHellman");
		
		// 初始化
		apg.init(512);
		// 生成AlgorithmParameters对象
		AlgorithmParameters ap = apg.generateParameters();
		// 获得字节数组
		byte[] b = ap.getEncoded();
		
		System.out.println( new BigInteger(b).toString() );
	}
}
