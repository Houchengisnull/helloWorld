package org.hc.learning.net.webservice.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://server.webservice.net.learning.hc.org")
public interface HelloWebService {

    @WebMethod
    String hello(@WebParam(name = "name")String name);

}
