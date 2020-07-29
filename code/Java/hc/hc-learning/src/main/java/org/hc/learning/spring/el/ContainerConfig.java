package org.hc.learning.spring.el;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 将配置文件注入Bean且读取集合
 */
@Component
@PropertySource("classpath:config/el/container.properties")
@ConfigurationProperties(prefix = "container")
public class ContainerConfig {

    @Value("hello")
    private String hello;

    private Map<String, String> map;

    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void output() {
        System.out.println("hello : " + hello);

        System.out.println("list[0] : " + list.get(0));
        System.out.println("list[1] : " + list.get(1));

        System.out.println("map.hello : " + map.get(list.get(0)));
        System.out.println("map.world : " + map.get(list.get(1)));
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

}
