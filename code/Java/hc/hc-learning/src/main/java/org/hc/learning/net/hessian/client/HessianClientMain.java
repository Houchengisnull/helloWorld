package org.hc.learning.net.hessian.client;

import com.caucho.hessian.client.HessianProxyFactory;
import org.hc.learning.net.hessian.client.service.HelloService;

import java.net.MalformedURLException;

public class HessianClientMain {

    public static void main(String[] args) {
        String url = "http://127.0.0.1:8090/helloService";
        HessianProxyFactory factory = new HessianProxyFactory();
        try {
            HelloService service = (HelloService) factory.create(HelloService.class, url);
            String result = service.sayHello("a simple client");
            System.out.println(result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
