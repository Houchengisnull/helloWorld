package org.hc.learning.spring.aop.dynamicproxy;

import java.lang.reflect.Proxy;

public class Main {

	public static void main(String[] args) {
		IHelloWorld helloWorld = new HelloWorld();
		LoggerHandler handler = new LoggerHandler(helloWorld);
		/**
		 * 动态生成一个IHelloWorld对象 proxy
		 */
		IHelloWorld proxy = (IHelloWorld) Proxy.newProxyInstance(
				Thread.currentThread().getContextClassLoader(),	// 获得当前类加载器
				helloWorld.getClass().getInterfaces(),
				handler);
		proxy.sayHello();
	}

}
