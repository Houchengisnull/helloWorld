package org.hc.learning.net.webservice.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

@Slf4j
public class CXFClient {

    public static void main(String[] args) throws Exception {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setAddress("http://127.0.0.1:8090/services/helloWebService");
        factory.setServiceClass(HelloWebService.class);
        
        //添加日志输入、输出拦截器，观察soap请求，soap响应内容
        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getOutFaultInterceptors().add(new LoggingOutInterceptor());

        HelloWebService helloWebService = factory.create(HelloWebService.class);
        String result = helloWebService.hello("test");
        log.debug("helloWebService.hello result :{}", result);
    }

}
