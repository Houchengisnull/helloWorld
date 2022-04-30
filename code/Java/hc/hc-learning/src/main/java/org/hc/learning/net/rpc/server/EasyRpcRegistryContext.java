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
        /**
         * 若要拆分注册中心与Provider，仍然要包装注册时的线程安全
         */
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
