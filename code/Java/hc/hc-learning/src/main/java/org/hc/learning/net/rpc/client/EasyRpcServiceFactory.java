package org.hc.learning.net.rpc.client;

import lombok.extern.slf4j.Slf4j;
import org.hc.learning.net.rpc.client.service.IHelloWorldService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
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

    public <T> T create(Class<T> clazz){
        T instance = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new ProxyHandle(clazz));
        return instance;
    }

    private class ProxyHandle implements InvocationHandler {

        private Class clazz;

        private ProxyHandle(Class clazz) {
            this.clazz = clazz;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // log.debug("call {}({})", method, args);
            /*
                判断成员方法是否有参数
            */
            if (args != null) {
                String argsValue = Arrays.asList(args).stream().map(ele -> String.valueOf(ele)).collect(Collectors.joining(", "));
                System.out.println(String.format("call %s(%s)", method.getName(), argsValue));
            } else {
                System.out.println(String.format("call %s", method.getName()));
            }

            /*
                remote process call
            */
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port));
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(clazz.getSimpleName());
            output.writeObject(method.getName());
            output.writeObject(method.getParameterTypes());
            output.writeObject(args);
            output.flush();

            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            Object result = input.readObject();
            output.close();
            input.close();
            return result;
        }

    }

}
