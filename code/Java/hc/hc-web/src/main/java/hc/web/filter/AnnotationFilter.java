package hc.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 注解配置过滤器
 * 需要启动类加上 @ServletComponentScan
 */
@Slf4j
@WebFilter(filterName = "annotationFilter", urlPatterns = "/*")
public class AnnotationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("### @Annotation Filter Before doFilter ###");
        chain.doFilter(request, response);
        log.debug("### @Annotation Filter After doFilter ###");
    }

    @Override
    public void destroy() {

    }
}
