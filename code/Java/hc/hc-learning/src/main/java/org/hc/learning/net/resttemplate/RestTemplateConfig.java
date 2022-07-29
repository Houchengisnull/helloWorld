package org.hc.learning.net.resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
/*@ComponentScan("org.hc.learning.net.resttemplate")*/
public class RestTemplateConfig {

    @Autowired
    private SimpleClientHttpRequestFactory factory;

    @Bean("factory")
    public SimpleClientHttpRequestFactory getFactory() {
        SimpleClientHttpRequestFactory f = new SimpleClientHttpRequestFactory();
        return f;
    }

    @Bean("template")
    public RestTemplate getRestTemplate() {
        RestTemplate t = new RestTemplate();
        t.setRequestFactory(factory);
        return t;
    }


}
