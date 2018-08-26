package org.hc.learning.spring.aop;

import org.springframework.stereotype.Service;

/**
 * 注解的被拦截类
 * @author Administrator
 *
 */
@Service
public class DemoAnnotationService {
	
	@Action(name="注解式拦截的add()操作")
	public void add(){
		System.out.println(" Annotation.add()");
	}
}
