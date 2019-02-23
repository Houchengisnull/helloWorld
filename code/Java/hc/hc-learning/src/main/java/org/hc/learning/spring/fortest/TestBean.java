package org.hc.learning.spring.fortest;

public class TestBean {
	private String content;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TestBean(String content) {
		super();
		this.content = content;
	}
	
}
