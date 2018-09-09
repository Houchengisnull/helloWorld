package org.hc.learning.安全.基础;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class A6加载密钥库 {
	public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		// 加载密钥文件
		FileInputStream is = new FileInputStream( "D:\\.keystore" );
		// 实例化KeyStore对象
		KeyStore ks = KeyStore.getInstance( KeyStore.getDefaultType() );
		// 加载密钥库,使用密码 password
		ks.load(is, "password".toCharArray() );
		// 关闭流
		is.close();
	}
}
