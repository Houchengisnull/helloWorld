package org.hc.learning.net.webservice.server;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

/**
 * 普通发布
 */
public class SimpleCXFWebService {

    public static void main(String[] args) {
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setAddress("http://127.0.0.1:8090/services/helloWebService");
        factory.setServiceBean(new HelloWebServiceImpl());
        factory.create();
    }

}
