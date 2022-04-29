package org.hc.learning.net.rpc.client;


import org.hc.learning.net.rpc.client.service.IHelloWorldService;

public class EasyRpcClient {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        Integer port = 10001;
        EasyRpcServiceFactory factory = new EasyRpcServiceFactory(host, port);

        IHelloWorldService service = factory.create(IHelloWorldService.class);
        String message = service.sayHello("Hou");
        System.out.println(message);
    }

}
