package org.hc.learning.安全.基础;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.CodeSigner;
import java.security.Timestamp;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Date;

public class A5时间戳与代码签名 {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws CertificateException, FileNotFoundException {
		// 构建CertificateFactory对象，并指定证书类型为 X.509
		CertificateFactory cf = CertificateFactory.getInstance("X509");
		// 生成CertPath对象
		CertPath cp = cf.generateCertPath( new FileInputStream("D:\\x.cer") );
		// 实例化数字时间戳
		Timestamp t = new Timestamp( new Date(), cp );
		// 实例化代码签名
		CodeSigner cs= new CodeSigner( cp, t );
		// 获得比对结果
		boolean status = cs.equals( new CodeSigner(cp , t ) );
		
		System.out.println( t );
	}
}
