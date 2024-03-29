package com.hc.config;

import com.hc.service.HelloService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({HelloService.class})
@Configuration
public class ImportConfig {
}
