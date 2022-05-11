package hc.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 注解配置过滤器
 * 需要启动类加上 @ServletComponentScan
 */
@WebFilter(filterName = "annotationFilter", urlPatterns = "/*")
public class AnnotationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("注解配置过滤器 >>>>>>>>>> request");
        chain.doFilter(request, response);
        System.out.println("注解配置过滤器 <<<<<<<<<< response");
    }

    @Override
    public void destroy() {

    }
}
