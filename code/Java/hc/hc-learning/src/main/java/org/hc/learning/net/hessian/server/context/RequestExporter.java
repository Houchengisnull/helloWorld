package org.hc.learning.net.hessian.server.context;

import lombok.extern.slf4j.Slf4j;
import org.hc.learning.net.hessian.server.context.HessianContext;
import org.springframework.remoting.caucho.HessianExporter;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.util.NestedServletException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RequestExporter extends HessianExporter implements HttpRequestHandler {

    @Override
    public void handleRequest(HttpServletRequest request,
                              HttpServletResponse response)
            throws ServletException, IOException {
        if (!"POST".equals(request.getMethod())) {
            throw new HttpRequestMethodNotSupportedException(
                    request.getMethod(),
                    new String[]{"POST"},
                    "HessianServiceExporter only supports POST requests");
        }

        response.setContentType(CONTENT_TYPE_HESSIAN);
        try {
            HessianContext.setRequest(request); //保存Request到Hessian线程上下文
            invoke(request.getInputStream(), response.getOutputStream());
        }
        catch (Throwable ex) {
            logger.error("Hessian skeleton invocation failed");
            throw new NestedServletException("Hessian skeleton invocation failed", ex);
        }
        finally {
            HessianContext.clear();
        }
    }
/*————————————————
    版权声明：本文为CSDN博主「viruer」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
    原文链接：https://blog.csdn.net/viruer/article/details/83612377*/
}
