package org.hc.learning.安全.基础.cert;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class D3CRL {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws CertificateException, CRLException, IOException {
		// 实例化，并指明证书类型为“X.509”
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		// 获得证书流
		FileInputStream in = new FileInputStream( "D:\\x.keystore" );
		// 获得证书撤消列表
		CRL crl = factory.generateCRL(in);
		// 关闭流
		in.close();
	}
}
