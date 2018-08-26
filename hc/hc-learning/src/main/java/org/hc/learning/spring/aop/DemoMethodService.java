package org.hc.learning.spring.aop;

import org.springframework.stereotype.Service;

/**
 * 关于方法的被拦截类
 * @author Administrator
 *
 */
@Service
public class DemoMethodService {
	public void add(){
		System.out.println(" Method.add()");
	}
}
