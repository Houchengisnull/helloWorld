package org.hc.learning.net.hessian.server.context;

import javax.servlet.ServletRequest;

public final class HessianContext {

    private ServletRequest _request;
    private static final ThreadLocal<HessianContext> _localContext
            = new ThreadLocal<HessianContext>() {
        @Override
        public HessianContext initialValue() {
            return new HessianContext();
        }
    };

    private HessianContext() {
    }

    public static void setRequest(ServletRequest request) {
        _localContext.get()._request = request;
    }

    public static ServletRequest getRequest() {
        return _localContext.get()._request;
    }

    public static void clear() {
        _localContext.get()._request = null;
    }
/*
————————————————
    版权声明：本文为CSDN博主「viruer」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
    原文链接：https://blog.csdn.net/viruer/article/details/83612377
*/

}
