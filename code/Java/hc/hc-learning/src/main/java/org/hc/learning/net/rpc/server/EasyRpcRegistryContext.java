package org.hc.learning.net.rpc.server;

import lombok.SneakyThrows;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册中心
 */
public class EasyRpcRegistryContext {

    private Map<String, Object> serviceHolder;

    @SneakyThrows
    public EasyRpcRegistryContext(Class[] classes){
        serviceHolder = new HashMap<>();
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
