package org.hc.learning.spring.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("org.hc.learning.spring.aop")
@EnableAspectJAutoProxy	//开启 Spring 对 AspectJ的支持
public class AopConfig {

}
