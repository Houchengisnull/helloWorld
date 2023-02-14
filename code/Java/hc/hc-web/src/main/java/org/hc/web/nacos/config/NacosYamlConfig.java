package org.hc.web.nacos.config;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Conditional(NacosCondition.class)
@NacosPropertySource(dataId = "hc-web", type = ConfigType.YAML, autoRefreshed = false)
@Configuration
public class NacosYamlConfig implements InitializingBean {

    @Value("${hc-web.username}")
    private String username;

    @NacosInjected
    private ConfigService configService;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("[NACOS]读取:[dataId:hc-web]{}", username);
    }
}
