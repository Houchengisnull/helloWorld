package org.hc.learning.spring.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DemoPublisher {
	@Autowired
	ApplicationContext context;
	
	public void publish(String msg) {
		context.publishEvent(new DemoEvent(this, msg));
	}
}
