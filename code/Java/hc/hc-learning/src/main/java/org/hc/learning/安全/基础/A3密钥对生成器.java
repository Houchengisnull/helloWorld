package org.hc.learning.安全.基础;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * 密钥对生成器
 * @author houcheng
 *
 */
public class A3密钥对生成器 {
	public static void main(String[] args) {
		try{
			// 实例化生成KeyPairGenerator对象
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
			// 初始化 KeyPairGenerator对象
			kpg.initialize( 1024 );
			// 生成KeyPair对象
			KeyPair keys = kpg.generateKeyPair();
			System.out.println( keys.getPublic() );
			System.out.println( keys.getPrivate() );
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
