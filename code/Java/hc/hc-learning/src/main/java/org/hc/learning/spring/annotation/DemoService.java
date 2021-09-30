package org.hc.learning.spring.annotation;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Service;

@Service("Demo")
public class DemoService implements BeanNameAware {

	private String beanName;

	public void outputResult() {
		System.out.println(beanName + "从组合注解中获得Bean");
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}
}
