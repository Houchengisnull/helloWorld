package org.hc.learning.algorithm.对称加密;

import static org.junit.Assert.assertEquals;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

public class PBECoder {
	@Test
	public void listService() {
		Provider provider = Security.getProvider("BC");
		Set<Service> services = provider.getServices();
		for (Service service : services) {
			System.out.println(service.getClassName() + " " +service.getAlgorithm());
		}
	}
	
	@Test
	public void test() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		String data = "Helloworld";
		System.out.println("原文 :" + data);
		byte[] bytes = data.getBytes();
		String password = "qwerty";
		byte[] salt = PBECoder.initSalt();
		
		System.out.println("盐  :" + Base64.toBase64String(salt));
		
		// 加密
		byte[] encrypt = PBECoder.encrypt(bytes, password, salt);
		System.out.println("加密  :" + Base64.toBase64String(encrypt));
		
		// 解密
		byte[] decrypt = PBECoder.decrypt(encrypt, password, salt);
		String output = new String(decrypt);
		System.out.println("解密 :" + output);
		assertEquals(data, output);
		
	}
	
	public static final String ALGORITHM = "PBEWITHMD5andDES";
	public static final int ITERATION_COUNT = 100;
	
	public static byte[] initSalt() {
		// 实例化安全随机数
		SecureRandom random = new SecureRandom();
		// 产出盐
		return random.generateSeed(8);
	}
	
	/**
	 * 转换密钥
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static Key toKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// 密钥材料转换
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
		// 实例化
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		// 生成密钥
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		return secretKey;
	}
	
	/**
	 * 加密
	 * @param data
	 * @param password
	 * @param salt
	 * @return
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws NoSuchPaddingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 */
	public static byte[] encrypt(byte[] data, String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		Key key = toKey(password);
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		return cipher.doFinal(data);
	}
	
	/**
	 * 解密
	 * @param data
	 * @param password
	 * @param salt
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] decrypt(byte[] data, String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		// 转换密钥
		Key key = toKey(password);
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
		
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		return cipher.doFinal(data);
	}
}
