package org.hc.learning.spring.aop.CGLib代理;

public class Main {

	public static void main(String[] args) {
		CGLibProxy cglibProxy = new CGLibProxy();
		HelloWorldWithoutInterface helloProxy = cglibProxy.getProxy(HelloWorldWithoutInterface.class);
		helloProxy.sayHello();
		helloProxy.sayFinal();
	}

}
