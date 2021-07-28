package org.hc.learning.net.hessian.server.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String msg) {
        System.out.println("receive message:" + msg);
        return "hello " + msg;
    }
}
