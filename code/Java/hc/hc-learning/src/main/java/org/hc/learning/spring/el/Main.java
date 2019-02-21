package org.hc.learning.spring.el;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ElConfig.class);
		ElConfig service = context.getBean(ElConfig.class);
		service.outputResource();
		context.close();
	}
}
