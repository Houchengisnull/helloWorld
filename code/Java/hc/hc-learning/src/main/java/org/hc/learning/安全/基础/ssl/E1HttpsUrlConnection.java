package org.hc.learning.安全.基础.ssl;

import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import static org.hc.learning.安全.基础.ssl.E1构建SSLSocketFactory.*;

@SuppressWarnings("unused")
public class E1HttpsUrlConnection {
	public static void main(String[] args) throws IOException {
		// 构建URL对象
		URL url = new URL( "https://www.sun.com/" );
		// 获得HttpsURLConnection实例化对象
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		// 打开输入模式 默认true
		System.out.println( "default doInput : " + conn.getDoInput() );
		conn.setDoInput( true );
		// conn.setOutput 默认false
		System.out.println( "default doInput : " + conn.getDoOutput() );
		conn.setDoOutput( true );
		
		// 在这里调用前面的介绍的getSSLSocketFactory() 方法
		// conn.setSSLSocketFactory( getSSLSocketFactory( password , keyStorePath , trustKeyStorePath ));
		// InputStream is = conn.getInputStream();
		// ...
		// is.close()
	}
}
