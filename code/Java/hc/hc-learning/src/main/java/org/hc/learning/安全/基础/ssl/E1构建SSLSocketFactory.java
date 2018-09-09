package org.hc.learning.安全.基础.ssl;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class E1构建SSLSocketFactory {
	/**
	 * 获得keyStore
	 * @param keyStorePath
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static KeyStore getKeyStore(String keyStorePath, String password) throws Exception{
		// 获得密钥库文件输入流
		FileInputStream is = new FileInputStream(keyStorePath);
		// 实例化密钥库
		KeyStore ks = KeyStore.getInstance("JKS");
		// 加载密钥库
		ks.load(is, password.toCharArray());
		// 关闭流
		is.close();
		return ks;
	}
	
	/**
	 * 获得SSLSocketFactory
	 * @param password
	 * @param keyStorePath
	 * @param trustKeyStorePath
	 * @return
	 * @throws Exception
	 */
	public static SSLSocketFactory getSSLSocketFactory(String password, 
			String keyStorePath, String trustKeyStorePath) throws Exception{
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
		KeyStore keyStore = getKeyStore( keyStorePath, password );
		keyManagerFactory.init(keyStore, password.toCharArray());
		// 初始化信任库
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance( "Sun509" );
		KeyStore trustkeyStore = getKeyStore( trustKeyStorePath, password );
		trustManagerFactory.init(trustkeyStore);
		// 初始化SSL上下文
		SSLContext ctx = SSLContext.getInstance( "SSL" );
		ctx.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
		SSLSocketFactory socketFactory = ctx.getSocketFactory();
		return socketFactory;
	}
	
}
