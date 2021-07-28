package org.hc.learning.net.hessian.server.service;

import org.hc.learning.net.hessian.server.context.HessianContext;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;

@Service
public class RequestHelloServiceImpl implements HelloService{

    @Override
    public String sayHello(String msg) {
        System.out.println("receive message:" + msg);
        ServletRequest request = HessianContext.getRequest();
        System.out.println("remote host:" + request.getRemoteHost());
        return "hello " + msg;
    }

}
