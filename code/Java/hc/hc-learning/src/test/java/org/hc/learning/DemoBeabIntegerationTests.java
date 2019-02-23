package org.hc.learning;

import org.hc.learning.spring.fortest.TestBean;
import org.hc.learning.spring.fortest.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) // JUnit环境下提供SpringTestContextFramework功能
@ContextConfiguration(classes = {TestConfig.class})
@ActiveProfiles("prod")
public class DemoBeabIntegerationTests {
	
	@Autowired
	private TestBean bean;
	
	@Test
	public void prodBeanShouldInject() {
		String expected = "from production profile";
		String actual = bean.getContent();
		Assert.assertEquals(expected, actual);
	}
}
