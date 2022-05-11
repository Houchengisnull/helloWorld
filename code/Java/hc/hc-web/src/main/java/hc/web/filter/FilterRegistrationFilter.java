package hc.web.filter;

import javax.servlet.*;
import java.io.IOException;

public class FilterRegistrationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("FilterRegisterBean配置过滤器 >>>>>>>>>> request");
        chain.doFilter(request, response);
        System.out.println("FilterRegisterBean配置过滤器 <<<<<<<<<< response");
    }

    @Override
    public void destroy() {

    }
}
