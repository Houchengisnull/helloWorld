package org.hc.learning.spring.el;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * pom.xml中添加了spring-boot-starter-data-jpa模块依赖，而且没有配置数据源连接信息的情况下,
 * 启动 Application 过程中会出现Cannot determine embedded database driver class for database type NON错误
 */
@SpringBootApplication(scanBasePackages = {"org.hc.learning.spring.el"},exclude = {DataSourceAutoConfiguration.class})
public class ContainerConfigApplication {

    public static void main(String[] args) {
        /*
         * 使用AnnotationConfigApplicationContext将不会根据字段名称进行匹配 必须使用@Value或其他注解
         */
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ContainerConfigApplication.class);
        ConfigurableApplicationContext context = builder.run(args);
        ContainerConfig bean = context.getBean(ContainerConfig.class);
        bean.output();
    }

}
