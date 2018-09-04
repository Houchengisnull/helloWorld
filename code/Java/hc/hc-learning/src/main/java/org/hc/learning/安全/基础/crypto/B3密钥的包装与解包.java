package org.hc.learning.安全.基础.crypto;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class B3密钥的包装与解包 {
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance( "DES" );
		// 生成secretKey对象
		SecretKey secretKey = keyGenerator.generateKey();
		// 实例化Cipher对象
		Cipher cipher = Cipher.getInstance( "DES" );
		
		// 包装操作
		// 初始化为 包装操作 ，并用secretKey加密
		cipher.init(Cipher.WRAP_MODE, secretKey);
		// 包装秘密密钥
		byte[] k = cipher.wrap(secretKey);
		
		// 得到字节数组k后，将其床底给需要解包的一方
		cipher.init(Cipher.UNWRAP_MODE, secretKey );
		Key key = cipher.unwrap(k, "DES", Cipher.SECRET_KEY);
		
		System.out.println("解包得到的密钥key : " + new BigInteger(key.getEncoded()));
		
		// 加密操作
		cipher.init(Cipher.ENCRYPT_MODE, secretKey );
		byte[] input = cipher.doFinal("Hello world".getBytes());
		System.out.println( "加密后数据 : " + new String(input) );
		
		// 解密
		cipher.init(Cipher.DECRYPT_MODE, secretKey );
		byte[] output = cipher.doFinal(input);
		System.out.println( "解密后数据 : " + new String(output) );
		
	}
}
