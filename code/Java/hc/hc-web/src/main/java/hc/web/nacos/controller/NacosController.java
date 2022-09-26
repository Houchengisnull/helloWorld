package hc.web.nacos.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.google.common.collect.Lists;
import hc.web.nacos.config.NacosCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@Conditional(NacosCondition.class)
@RestController
public class NacosController {

    @NacosInjected
    private ConfigService configService;

    @NacosInjected
    private NamingService namingService;

    @Value("${hc-web.username}")
    private String username;

    @NacosValue(value = "${hc-web.username}", autoRefreshed = false)
    private String nacosUsername;

    @NacosValue(value = "${hc-web.username}", autoRefreshed = true)
    private String autoRefreshedNacosUsername;

    @GetMapping("/nacos/discovery/{serviceName}")
    public List<Instance> discovery(@PathVariable String serviceName) throws NacosException {
        List<Instance> allInstances = namingService.getAllInstances(serviceName);
        return allInstances;
    }

    @GetMapping("/nacos/config")
    public List<String> listUser(){
        List<String> strings = Lists.newArrayList();
        strings.add(username);
        strings.add(nacosUsername);
        strings.add(autoRefreshedNacosUsername);
        return strings;
    }

    @GetMapping("/nacos/config/version")
    public String listVersion() throws NacosException {
        String versionByConfigService = configService.getConfig("hc-web.json", "DEFAULT_GROUP", 100);
        log.info(versionByConfigService);

        return versionByConfigService;
    }

}
