package org.hc.learning.net.hessian.server;

import org.hc.learning.net.hessian.server.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianServiceExporter;

@SpringBootApplication/*(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})*/
public class HessianServerApplication {

    @Autowired
    private HelloService helloService;

    @Bean("/helloService")
    public HessianServiceExporter hessianServiceExporter() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(helloService);
        exporter.setServiceInterface(HelloService.class);
        return exporter;
    }

    public static void main(String[] args) {
        System.setProperty("server.port","8090");
        ConfigurableApplicationContext run = SpringApplication.run(HessianServerApplication.class);
    }

}
