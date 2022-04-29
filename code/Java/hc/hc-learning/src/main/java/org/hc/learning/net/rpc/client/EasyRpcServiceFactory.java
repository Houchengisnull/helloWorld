package org.hc.learning.net.rpc.client;

import lombok.extern.slf4j.Slf4j;
import org.hc.learning.net.rpc.client.service.IHelloWorldService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class EasyRpcServiceFactory {

    private String host;
    private Integer port;

    public EasyRpcServiceFactory(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

  /*  public <T> T create(Class<T> clazz){
        T instance = null;

        instance = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new ProxyHandle());

        return instance;
    }*/

    public IHelloWorldService create(Class<IHelloWorldService> clazz) {
        IHelloWorldService instance = null;

        instance = (IHelloWorldService) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new ProxyHandle());

        return instance;
    }

    private static class ProxyHandle implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // log.debug("call {}({})", method, args);
            String argsValue = Arrays.asList(args).stream().map(ele -> String.valueOf(ele)).collect(Collectors.joining(", "));
            System.out.println(String.format("call %s(%s)", method.getName(), argsValue));

            return "";
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

}
