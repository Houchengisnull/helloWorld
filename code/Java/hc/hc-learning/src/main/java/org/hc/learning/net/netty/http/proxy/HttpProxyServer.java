package org.hc.learning.net.netty.http.proxy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 参考
 * https://github.com/shuaicj/http-proxy-netty/tree/master/src/main/java/shuaicj/hobby/http/proxy/netty
 * https://github.com/asarkar/kotlin/tree/master/netty-learning/proxy
 */
@Slf4j
public class HttpProxyServer {

    private static final String remoteIp = "127.0.0.1";
    private static final int remotePort = 8090;
    private static final int localPort = 8007;

    public static void main(String[] args) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        ChannelInitializer initializer = new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) {
                log.info("Http Proxy Server Init Channel.");
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new HttpServerCodec());
                pipeline.addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));
                pipeline.addLast("compressor", new HttpContentCompressor()); // 压缩
                pipeline.addLast("handler", new HttpProxyFrontendHandler(remoteIp, remotePort));
            }
        };

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioServerSocketChannel.class)
                /*.handler(new LoggingHandler(LogLevel.INFO))*/
                .childOption(ChannelOption.AUTO_READ, false)
                .childHandler((initializer));

        try {
            ChannelFuture future = bootstrap.bind(localPort).sync();

            log.info("Netty Proxy Server Bind {} Successful.", localPort);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

}
