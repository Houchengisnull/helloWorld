package org.hc.learning.spring.aop.CGLib代理;

import org.hc.learning.spring.aop.dynamicproxy.HelloWorld;
import org.hc.learning.spring.aop.dynamicproxy.IHelloWorld;

public class Main {

	public static void main(String[] args) {
		CGLibProxy cglibProxy = new CGLibProxy();
		IHelloWorld helloProxy = cglibProxy.getProxy(HelloWorld.class);
		helloProxy.sayHello();
	}

}
