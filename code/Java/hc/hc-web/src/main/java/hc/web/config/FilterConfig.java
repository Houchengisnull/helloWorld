package hc.web.config;

import hc.web.filter.FilterRegistrationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean registerFilter(){
        FilterRegistrationFilter filter = new FilterRegistrationFilter();
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        return registration;
    }

    /**
     * 在过滤中使用Spring依赖注入
     * 1. 使用Spring装配Filter的实例
     * 2. proxy.setTargetBeanName(bean)指定被代理的实例
     * 3. FilterRegistrationFilter.setFilter(proxy)将注册代理过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean registerDelegatingFilterProxy(){
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetBeanName("springDelegatingProxyFilter");

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(proxy);
        registration.addUrlPatterns("/*");
        return registration;
    }

}
