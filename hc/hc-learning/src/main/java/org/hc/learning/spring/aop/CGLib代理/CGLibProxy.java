package org.hc.learning.spring.aop.CGLib代理;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;



public class CGLibProxy implements MethodInterceptor{
	
	@SuppressWarnings("unchecked")
	public <T> T getProxy(Class<T> cls){
		/**
		 * MethodInterceptor 继承了CallBack接口
		 * 由于编译器报错，故添加强制类型转化
		 */
		return (T) Enhancer.create(cls,  this);
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println( "before" );
		Object result = proxy.invokeSuper(obj, args);	//被代理对象及其参数
		System.out.println( "after" );
		return result;
	}

}
