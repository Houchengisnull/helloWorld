package hc.web.nacos.config;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class NacosCondition implements Condition {

    private static final String PROFILE = "nacos";
    
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return ArrayUtil.contains(context.getEnvironment().getActiveProfiles(), PROFILE);
    }
    
}
