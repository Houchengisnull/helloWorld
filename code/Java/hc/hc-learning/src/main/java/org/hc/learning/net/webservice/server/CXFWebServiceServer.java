package org.hc.learning.net.webservice.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Springboot集成CXF发布WebService
 */
@SpringBootApplication
public class CXFWebServiceServer {

    public static void main(String[] args) {
        System.setProperty("server.port","8090");
        ConfigurableApplicationContext run = SpringApplication.run(CXFWebServiceServer.class);
    }
}
