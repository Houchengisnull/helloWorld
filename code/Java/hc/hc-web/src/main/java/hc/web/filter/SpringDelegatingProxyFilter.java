package hc.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import java.io.IOException;

/**
 * 在过滤器中使用spring注解
 */
@Slf4j
@Component("springDelegatingProxyFilter")
public class SpringDelegatingProxyFilter implements Filter {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("@Value(\"server.servlet.context-path\"):{}", contextPath);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
