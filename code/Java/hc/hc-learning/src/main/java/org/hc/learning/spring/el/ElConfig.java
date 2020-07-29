package org.hc.learning.spring.el;

import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import javax.inject.Inject;

@Configuration
@ComponentScan("org.hc.learning.spring.el")
@PropertySource("classpath:config/el/test.properties")
public class ElConfig {
	public String getNormal() {
		return normal;
	}

	public void setNormal(String normal) {
		this.normal = normal;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public double getRandomNumber() {
		return randomNumber;
	}

	public void setRandomNumber(double randomNumber) {
		this.randomNumber = randomNumber;
	}

	public String getFromAnother() {
		return fromAnother;
	}

	public void setFromAnother(String fromAnother) {
		this.fromAnother = fromAnother;
	}

	public Resource getTestFile() {
		return testFile;
	}

	public void setTestFile(Resource testFile) {
		this.testFile = testFile;
	}

	public Resource getTestUrl() {
		return testUrl;
	}

	public void setTestUrl(Resource testUrl) {
		this.testUrl = testUrl;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Value("I love you")
	private String normal;
	@Value("#{systemProperties['os.name']}")
	private String osName;
	
	@Value("#{ T(java.lang.Math).random() * 100.0}")
	private double randomNumber;
	
	@Value("#{demoService.another}")
	private String fromAnother;
	
	@Value("classpath:config/el/test.txt")
	private Resource testFile;
	
	@Value("http://www.baidu.com")
	private Resource testUrl;

	@Value("${book.name}")
	private String bookName;

	private int width;

	private int height;

	/**
	 * setter 多参方法注入
	 * 需要通过@Inject|@Autowired提醒spring context对该setter进行注入
	 */
	@Inject
	public void setSize(@Value("100") int width,@Value("200") int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	@Inject
	private Environment environment;
	
	public static PropertySourcesPlaceholderConfigurer properyConfigure() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public void outputResource() {
		System.out.println(normal);
		System.out.println(osName);
		System.out.println(randomNumber);
		System.out.println(fromAnother);
		try {
			System.out.println(IOUtils.toString(testFile.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			System.out.println(IOUtils.toString(testUrl.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(bookName);
		System.out.println(environment.getProperty("book.author"));

		System.out.println("width : " + width);
		System.out.println("height : " + height);
	}

}
