package org.hc.learning.spring.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WiselyConfiguration
public class DemoConfig {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig.class);
		DemoService service = context.getBean(DemoService.class);
		service.outputResult();
		context.close();
	}
}
