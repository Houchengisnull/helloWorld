package hc.web;

import hc.web.controller.TestController;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/*@SpringBootApplication*/
@ComponentScan(basePackages = {"hc.web.controller","hc.web.config"}
, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {TestController.class})})
/*
 * 由于 DataSourceAutoConfiguration将读取 .yml .properties 中的spring.datasource.*属性并自动配置单数据源。
 * 故需要禁止
 */
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        builder.bannerMode(Banner.Mode.OFF).run(args);
        builder.run(args);
        ConfigurableApplicationContext context = builder.context();

        /**
         * 关闭Web应用
         */
        // context.close();
    }
}
