package org.hc.learning.安全.基础.crypto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class B3密钥输入输出流 {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException {
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		SecretKey secretKey = kg.generateKey();
		Cipher cipher = Cipher.getInstance("DES");
		
		// 输入
		// 解密
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		CipherInputStream cis = new CipherInputStream(new FileInputStream("secret"),cipher);
		// 使用DataInputStream对象包装CipherInputStream对象
		DataInputStream dis = new DataInputStream(cis);
		String output = dis.readUTF();
		dis.close();
		
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		// 
		String input = "1234567890";
		CipherOutputStream cos = new CipherOutputStream( new FileOutputStream(new File("secret")),cipher);
		DataOutputStream dos = new DataOutputStream( cos );
		dos.writeUTF( input );
		dos.close();
	}
}
