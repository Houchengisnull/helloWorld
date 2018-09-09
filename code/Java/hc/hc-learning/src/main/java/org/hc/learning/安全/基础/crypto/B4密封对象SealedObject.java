package org.hc.learning.安全.基础.crypto;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

public class B4密封对象SealedObject {
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, IOException, ClassNotFoundException, BadPaddingException {
		// 待加密字符串
		String input = "SealedObject";
		// 实例化KeyGenerator对象,使用DES算法
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		// 创建密钥
		SecretKey key = kg.generateKey();
		// 创建秘密密钥
		Cipher cipher1 = Cipher.getInstance( key.getAlgorithm() );
		// 初始化 加密模式
		cipher1.init(Cipher.ENCRYPT_MODE, key);
		
		// 构建 SealedObject 对象
		SealedObject sealedObject = new SealedObject(input, cipher1 );
		
		// 解密 SealedObejct 对象
		/*Cipher cipher2 = Cipher.getInstance( key.getAlgorithm() );
		cipher2.init(Cipher.DECRYPT_MODE, key);
		String output = (String) sealedObject.getObject( cipher2 );*/
		
		String output = (String)sealedObject.getObject( key );
		System.out.println( output );
		
		
		
	}
}
