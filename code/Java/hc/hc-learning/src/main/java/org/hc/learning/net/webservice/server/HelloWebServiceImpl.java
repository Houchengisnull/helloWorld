package org.hc.learning.net.webservice.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.jws.WebService;

@WebService(serviceName = "HelloWebService"
    , targetNamespace = "http://server.webservice.net.learning.hc.org"
    , endpointInterface = "org.hc.learning.net.webservice.server.HelloWebService")

@Slf4j
@Service
public class HelloWebServiceImpl implements HelloWebService{

    @Override
    public String hello(String name) {
        log.debug("receive {}'s message", name);

        return "This is a webservice [HelloWebService.hello]";
    }
}
