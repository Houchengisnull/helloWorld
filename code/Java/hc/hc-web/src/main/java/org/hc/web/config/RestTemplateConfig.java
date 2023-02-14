package org.hc.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * 对于RestTemplate中的getFor*\postFor*\exchange
 * 分别适用于指定method的场景与通用的RestFul调用
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(){
        /**
         * 配置代理
         */
        Proxy p = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 9091));
        SimpleClientHttpRequestFactory f = new SimpleClientHttpRequestFactory();
        f.setProxy(p);
        RestTemplate t = new RestTemplate(f);

        return t;
    }

}
