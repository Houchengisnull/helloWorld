package hc.web.nacos.config;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class NacosJsonConfig implements InitializingBean {

    @NacosInjected
    private ConfigService configService;

    /**
     * 发布配置信息hc-web.json
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] bytes = FileUtil.readBytes("hc-web.json");
        JSONObject version = (JSONObject) JSONObject.parse(bytes);
        log.info("[NACOS]发布:[hc-web.json]{}", version.toJSONString());
        configService.publishConfig("hc-web.json", "DEFAULT_GROUP", version.toJSONString());
    }

}
