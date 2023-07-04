package org.hc.learning.net.netty.http;

import io.netty.channel.nio.NioEventLoopGroup;
import org.hc.learning.net.netty.util.BootstrapUtil;

public class HttpsServer {

    public static void main(String[] args) {
        NioEventLoopGroup parentGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        HttpsServerHandlerInit httpsServerHandlerInit = new HttpsServerHandlerInit();
        BootstrapUtil.bootstrapHttpServer(parentGroup, workGroup, httpsServerHandlerInit);
    }

}
