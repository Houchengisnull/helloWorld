package org.hc.learning.spring.profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {
	@Bean
	@Profile("dev")
	public DemoBean devDemoBean() {
		return new DemoBean("dev");
	}
	
	@Bean
	@Profile("prod")
	public DemoBean prodDemoBean() {
		return new DemoBean("prod");
	}
}

class DemoBean {
	private String context;
	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public DemoBean(String context) {
		this.context = context;
	}
}
