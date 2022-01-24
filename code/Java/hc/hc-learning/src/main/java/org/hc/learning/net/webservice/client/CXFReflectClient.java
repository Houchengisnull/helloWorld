package org.hc.learning.net.webservice.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * 通过反射方式生成stub
 */
@Slf4j
public class CXFReflectClient {

    public static void main(String[] args) throws Exception {
        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        Client client = factory.createClient("http://127.0.0.1:8090/services/helloWebService?wsdl");
        Object[] result = client.invoke("hello", "invoke");
        log.debug("helloWebService.hello result :{}", result[0]);
    }
}
