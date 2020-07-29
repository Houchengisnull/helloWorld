package org.hc.learning.spring.el;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 通过Annotation启动
 */
public class ElConfigApplication {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ElConfig.class);
		// ElConfig
		ElConfig service = context.getBean(ElConfig.class);
		service.outputResource();
		context.close();
	}
}

