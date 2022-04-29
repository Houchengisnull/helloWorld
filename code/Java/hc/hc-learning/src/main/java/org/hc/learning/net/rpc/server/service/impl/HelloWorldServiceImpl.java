package org.hc.learning.net.rpc.server.service.impl;

import org.hc.learning.net.rpc.client.service.IHelloWorldService;

public class HelloWorldServiceImpl implements IHelloWorldService {

    @Override
    public String sayHello(String name) {
        return "Nice to meet u";
    }

}
