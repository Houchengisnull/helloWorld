package org.hc.learning.net.webservice.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Stub
 */
@WebService(targetNamespace = "http://server.webservice.net.learning.hc.org")
public interface HelloWebService {

    @WebMethod
    String hello(@WebParam(name = "name") String name);

}