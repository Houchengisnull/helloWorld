package org.hc.learning.安全.基础.ssl;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;

public class E1构建密钥管理工厂 {
	public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException, UnrecoverableKeyException {
		// 实例化KeyManagerFactory对象
		KeyManagerFactory factory = KeyManagerFactory.getInstance("SunX509");
		// 加载密钥库文件
		FileInputStream is = new FileInputStream("D:\\x.keystore");
		// 实例化KeyStore对象
		KeyStore ks = KeyStore.getInstance("JKS");
		// 加载密钥库
		ks.load( is , "password".toCharArray() );
		factory.init(ks, "password".toCharArray());
	}
}
