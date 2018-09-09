package org.hc.learning.安全.基础.cert;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class D1加载证书 {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws CertificateException, IOException {
		// 实例化,并指定证书类型为“X.509”
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		// 获得证书输入流
		FileInputStream in = new FileInputStream("D:\\x.keystore");
		// 获得证书
		Certificate certificate = certificateFactory.generateCertificate(in);
		in.close();
	}
}
