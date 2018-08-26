package org.hc.learning.spring.aop.dynamicproxy;

public class HelloWorld implements IHelloWorld{
	@Override
	public void sayHello() {
		System.out.println("Hello world");
	}
}
