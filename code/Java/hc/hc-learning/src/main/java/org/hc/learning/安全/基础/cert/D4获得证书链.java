package org.hc.learning.安全.基础.cert;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class D4获得证书链 {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws CertificateException, IOException {
		// 实例化CertificateFactory对象,并指定证书类型为"X.509"
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		// 获得证书输入流
		FileInputStream in = new FileInputStream("D:\\x.keystore");
		// 获得CertPath对象
		CertPath certPath = certificateFactory.generateCertPath(in);
		// 关闭流
		in.close();
	}
}
