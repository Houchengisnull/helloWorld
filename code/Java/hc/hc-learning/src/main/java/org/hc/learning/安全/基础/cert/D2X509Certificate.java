package org.hc.learning.安全.基础.cert;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class D2X509Certificate {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		// 加载密钥库文件
		FileInputStream is = new FileInputStream( "D:\\x.keystore" );
		// 实例化KeyStore
		KeyStore ks = KeyStore.getInstance("JKS");
		// 加载密钥库
		ks.load(is, "password".toCharArray() );
		is.close();
		
		X509Certificate certificate = (X509Certificate) ks.getCertificate("alias");
		Signature signature = Signature.getInstance( certificate.getSigAlgName() );
	}
}
