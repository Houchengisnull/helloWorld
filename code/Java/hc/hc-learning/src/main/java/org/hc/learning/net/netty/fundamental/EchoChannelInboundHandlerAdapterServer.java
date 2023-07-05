package org.hc.learning.net.netty.fundamental;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.ssl.SslContext;
import lombok.SneakyThrows;
import org.hc.learning.net.netty.util.BootstrapUtil;
import org.hc.learning.net.netty.util.ServerUtil;

public class EchoChannelInboundHandlerAdapterServer {

    @SneakyThrows
    public static void main(String[] args) {
        final SslContext sslCtx = ServerUtil.buildSslContext();

        NioEventLoopGroup parentGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        EchoChannelInboundHandlerAdapter adapter = new EchoChannelInboundHandlerAdapter();
        BootstrapUtil.bind(parentGroup, workGroup, adapter);

    }
}
