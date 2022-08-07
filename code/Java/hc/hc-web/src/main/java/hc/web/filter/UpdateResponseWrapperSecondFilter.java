package hc.web.filter;

import hc.web.wrapper.UpdateResponseBodyWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 在过滤器中修改response body
 *
 * 注意事项:
 * - 必须设置response header的ContentLength
 */
@Slf4j
public class UpdateResponseWrapperSecondFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        UpdateResponseBodyWrapper responseWrapper = new UpdateResponseBodyWrapper((HttpServletResponse) response);
        chain.doFilter(request, responseWrapper);

        byte[] buffer = responseWrapper.getResponseBody();
        String responseBody = new String(buffer);
        log.info("Original response body:{}", responseBody);
        log.info("Original response body size:{}", buffer.length);

        // write
        String message = responseBody + "!";

        responseWrapper.setResponseBody(message);
    }

}
