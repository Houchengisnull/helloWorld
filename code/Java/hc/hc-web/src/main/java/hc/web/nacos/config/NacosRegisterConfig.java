package hc.web.nacos.config;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.naming.NamingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

@Slf4j
@Configuration
public class NacosRegisterConfig implements InitializingBean {

    @NacosInjected
    private NamingService namingService;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port}")
    private Integer port;

    @Override
    public void afterPropertiesSet() throws Exception {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        log.info("[NACOS]注册:[{}]{}:{}", applicationName, hostAddress, port);
        namingService.registerInstance(applicationName, hostAddress, port);
    }

}
