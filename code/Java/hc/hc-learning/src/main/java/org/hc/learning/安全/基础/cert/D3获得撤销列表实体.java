package org.hc.learning.安全.基础.cert;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;

public class D3获得撤销列表实体 {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws CertificateException, CRLException, IOException {
		// 实例化,并指定证书类型为  “X.509”
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		// 获得证书输入流
		FileInputStream in = new FileInputStream("D:\\x.keystore");
		// 获得证书
		X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(in);
		// 获得证书撤消列表
		X509CRL x509CRL = (X509CRL) certificateFactory.generateCRL(in);
		// 获得证书撤消列表实体
		X509CRLEntry x509CRLEntry = x509CRL.getRevokedCertificate(certificate);
		// 关闭流
		in.close();
	}
}
