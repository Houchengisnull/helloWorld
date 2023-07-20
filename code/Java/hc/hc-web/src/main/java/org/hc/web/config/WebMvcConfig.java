package org.hc.web.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer, ErrorPageRegistrar {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/view/index").setViewName("index");
        registry.addViewController("/view/server_push").setViewName("server_push");
        registry.addViewController("/view/server_push/interval").setViewName("server_push/interval");
        registry.addViewController("/view/server_push/sse").setViewName("server_push/sse");

    }

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage[] errorPages = new ErrorPage[]{new ErrorPage(HttpStatus.NOT_FOUND, "/WEB-INF/view/404.jsp")};
        registry.addErrorPages(errorPages);
    }
}
