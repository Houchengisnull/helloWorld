package org.hc.learning.net.webservice.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Stub
 */

/**
 * 如果不使用@WebService指定targetNamespace
 * 默认命名空间为 http://server.webservice.net.learning.hc.org
 */
@WebService(targetNamespace = "http://server.webservice.net.learning.hc.org")
public interface HelloWebService {

    @WebMethod
    /**
     * @WebResult
     * 指定WebService的响应消息的name, targetNamespace
     * 有趣的是: 其中一个WebService需要使用该注解指定响应消息，而另一个不需要
     */
    /*@WebResult(name = "out", targetNamespace = "http://server.webservice.net.learning.hc.org")*/
    String hello(@WebParam(name = "name") String name);

}