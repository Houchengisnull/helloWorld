package org.hc.learning.spring.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.hc.learning.spring.event")
class EventConfig {
	
}

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EventConfig.class);
		DemoPublisher publisher = context.getBean(DemoPublisher.class);
		publisher.publish("Hello world");
		context.close();
	}
}