package org.hc.learning.net.hessian.server;

import org.hc.learning.net.hessian.server.context.RequestExporter;
import org.hc.learning.net.hessian.server.service.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import javax.annotation.Resource;

@SpringBootApplication/*(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})*/
public class RequestHessianServerApplication {

    @Resource(name = "requestHelloServiceImpl")
    private HelloService helloService;

    @Bean("/helloService")
    public RequestExporter hessianServiceExporter() {
        RequestExporter exporter = new RequestExporter();
        exporter.setService(helloService);
        exporter.setServiceInterface(HelloService.class);
        return exporter;
    }

    public static void main(String[] args) {
        System.setProperty("server.port","8090");
        ConfigurableApplicationContext run = SpringApplication.run(RequestHessianServerApplication.class);
        /*String[] beanDefinitionNames = run.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }*/
    }
}
