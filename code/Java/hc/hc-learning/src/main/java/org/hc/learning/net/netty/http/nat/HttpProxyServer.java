package org.hc.learning.net.netty.http.nat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.hc.learning.net.netty.util.BootstrapUtil;

@Slf4j
public class HttpProxyServer {

    private static final String remoteIp = "127.0.0.1";
    private static final int remotePort = 8090;

    public static void main(String[] args) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        BootstrapUtil.bind(eventExecutors, new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) {
                log.info("Http Proxy Server Init Channel.");
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new HttpServerCodec());
                pipeline.addLast("aggregator", new HttpObjectAggregator(10*1024*1024));
                pipeline.addLast("compressor",new HttpContentCompressor()); // 压缩
                pipeline.addLast("handler", new HttpProxyFrontendHandler(remoteIp, remotePort));
            }
        });
    }

}
