package org.hc.learning.安全.基础.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class E1HttpUrlConnection {
	public static void main(String[] args) throws IOException {
		URL url = new URL("https://www.cnblogs.com/jeffen/p/6937788.html");
		// 底层将通过 句柄handler 打开TCP连接
		// 首先URLConnection是一个抽象类
		// 猜测: 那么一定是 JVM 根据url的内容 通过合适类 来创建对象并返回
		// 实际： 只有一种 HttpsURLConnectionImpl
		URLConnection connection = url.openConnection();
		
		// 向下转型
		HttpURLConnection httpConnection = (HttpURLConnection) connection;
		System.out.println( httpConnection.getResponseCode() );
		System.out.println();
		InputStream input = httpConnection.getInputStream();
		byte[] bytes = new byte[1024];
		int i = -1;
		while ( (i = input.read(bytes)) != -1 ){
			System.out.println( new String(bytes,0,i) );
		}
		httpConnection.disconnect();
	}
}
