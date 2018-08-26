package org.hc.learning.spring.aop.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理 例子
 * @author Administrator
 *
 */
public class LoggerHandler implements InvocationHandler{
	
	/**
	 * 被代理的对象
	 */
	private Object target;
	
	public LoggerHandler( Object target ) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("startLog()");
		Object result = method.invoke(target, args);
		System.out.println("endLog()");
		return result;
	}

}
