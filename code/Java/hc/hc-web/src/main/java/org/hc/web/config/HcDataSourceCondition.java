package org.hc.web.config;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class HcDataSourceCondition implements Condition {

    private static final String PROFILE = "dao";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return ArrayUtil.contains(context.getEnvironment().getActiveProfiles(), PROFILE);
    }
}
