package org.hc.learning.net.hessian.client;

import org.hc.learning.net.hessian.client.service.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

@SpringBootApplication
public class HessianClientApplication {

    @Bean
    public HessianProxyFactoryBean hessianClient() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl("http://127.0.0.1:8090/helloService");
        factoryBean.setServiceInterface(HelloService.class);
        return factoryBean;
    }

    public static void main(String[] args) {
        System.setProperty("server.port", "8091");
        ConfigurableApplicationContext run = SpringApplication.run(HessianClientApplication.class, args);
        HelloService service = run.getBean(HelloService.class);
        String result = service.sayHello("world");
        System.out.println(result);
    }

}
