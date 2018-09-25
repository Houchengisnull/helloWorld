package org.hc.learning.algorithm.对称加密;

import static org.junit.Assert.assertEquals;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

/**
 * DES安全编码组件
 *
 * 注意,密钥生成算法和加密/解密算法很有可能采用不同算法。
 */
public class DESCoder {
	
	@Test
	public void printBouncy() {
		Provider provider = Security.getProvider("BC");
		Set<Service> services = provider.getServices();
		for (Service service : services) {
			System.out.println(service.getClassName() + " " +service.getAlgorithm());
		}
	}
	
	@Test
	public void test() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
		String string = "Hello world";
		byte[] data = string.getBytes();
		System.out.println("原文:\t" + string);
		// 生成密钥
		byte[] key = DESCoder.initKey();
		System.out.println("密钥:\t" + Base64.toBase64String(key));
		// 加密
		byte[] encrypt = DESCoder.encrypt(data, key);
		System.out.println("加密:\t" + Base64.toBase64String(encrypt));
		// 解密
		byte[] decrypt = DESCoder.decrypt(encrypt, key);
		String output = new String(decrypt);
		System.out.println("解密:\t" + output);
		assertEquals(string, output);
	}
	
	public static final String KEY_ALGORITHM = "DES";
	/**
	 * 加密/解密算法/工作模式/填充方式
	 */
	/*public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";*/
	public static final String CIPHER_ALGORITHM = "DES";
	
	/**
	 * 转换密钥
	 * @param key 二进制密钥
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws InvalidKeyException 
	 */
	public static Key toKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
		// 实例化DES密钥材料
		DESKeySpec dks = new DESKeySpec(key);
		// 实例化秘密密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		// 生成秘密密钥
		SecretKey secretKey = keyFactory.generateSecret(dks);
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
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
		// 还原密钥
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
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
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
	    // 还原密钥	
		Key k = toKey(key);
		// 实例化
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
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
		KeyGenerator kg = KeyGenerator.getInstance(CIPHER_ALGORITHM);
		kg.init(64);
		SecretKey key = kg.generateKey();
		return key.getEncoded();
	}
}
