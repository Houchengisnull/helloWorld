package org.hc.learning.net.netty.http;

import io.netty.channel.nio.NioEventLoopGroup;
import org.hc.learning.net.netty.util.BootstrapUtil;

public class HttpServer {

    public static void main(String[] args) {
        NioEventLoopGroup parentGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        HttpServerHandlerInit httpServerHandlerInit = new HttpServerHandlerInit();
        BootstrapUtil.bootstrapHttpServer(parentGroup, workGroup, httpServerHandlerInit);
    }

}
