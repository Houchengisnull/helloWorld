package org.hc.learning.algorithm.对称加密;

import static org.junit.Assert.assertEquals;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.security.Provider.Service;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

public class AESCoder {
	
	@Test
	public void listService() {
		Provider provider = Security.getProvider("BC");
		Set<Service> services = provider.getServices();
		for (Service service : services) {
//			System.out.println(service.getClassName() + " " +service.getAlgorithm());
			if (service.getAlgorithm().contains("AES")) {
				System.out.print(service.getType());
				System.out.print("\t");
			}
			System.out.println(service.getAlgorithm());
		}
	}
	
	@Test
	public void test() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
		String string = "Hello world";
		byte[] data = string.getBytes();
		System.out.println("原文:\t" + string);
		// 生成密钥
		byte[] key = AESCoder.initKey();
		System.out.println("密钥:\t" + Base64.toBase64String(key));
		// 加密
		byte[] encrypt = AESCoder.encrypt(data, key);
		System.out.println("加密:\t" + Base64.toBase64String(encrypt));
		// 解密
		byte[] decrypt = AESCoder.decrypt(encrypt, key);
		String output = new String(decrypt);
		System.out.println("解密:\t" + output);
		assertEquals(string, output);
	}
	
	public static final String KEY_ALGORITHM = "AES";
	/**
	 * 加密/解密算法/工作模式/填充方式
	 */
	/*public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";*/
	public static final String CIPHER_ALGORITHM = "AES";
	
	/**
	 * 转换密钥
	 * @param key 二进制密钥
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws InvalidKeyException 
	 */
	public static Key toKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
		SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
		return secretKey;
	}
	
	/**
	 * 解密
	 * @param data
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidKeySpecException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchProviderException 
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
		// 还原密钥
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
		// 初始化
		cipher.init(Cipher.DECRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}
	
	/**
	 * 加密
	 * @param data
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidKeySpecException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchProviderException 
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
	    // 还原密钥	
		Key k = toKey(key);
		// 实例化
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}
	
	/**
	 * 初始化密钥
	 * @return
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static byte[] initKey() throws NoSuchAlgorithmException, NoSuchProviderException {
		/**
		 * 若要使用 64位密钥 则使用Bouncy Castle 密钥
		 */
		KeyGenerator kg = KeyGenerator.getInstance(CIPHER_ALGORITHM, "BC");
		// 128 192
		kg.init(256);
		SecretKey key = kg.generateKey();
		return key.getEncoded();
	}
}
