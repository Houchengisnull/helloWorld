package org.hc.web.filter;

import lombok.extern.slf4j.Slf4j;
import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class FilterRegistrationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("FilterRegisterBean配置过滤器 >>>>>>>>>> request");
        chain.doFilter(request, response);
        log.debug("FilterRegisterBean配置过滤器 <<<<<<<<<< response");
    }

    @Override
    public void destroy() {

    }
}
