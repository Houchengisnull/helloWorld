package org.hc.learning.net.rpc.server;

import lombok.SneakyThrows;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册中心
 */
public class EasyRpcRegisterContext {

    private ConcurrentHashMap<String, Object> serviceHolder;

    @SneakyThrows
    public EasyRpcRegisterContext(Class[] classes){
        serviceHolder = new ConcurrentHashMap<>();
        for (Class clazz : classes) {
            Object instance = clazz.newInstance();
            Class[] interfaces = clazz.getInterfaces();
            String simpleName = interfaces[0].getSimpleName();
            serviceHolder.put(simpleName, instance);
        }
    }

    public Object getInstance(String clazzName) {
        return serviceHolder.get(clazzName);
    }

}
