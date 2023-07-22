package org.hc.learning.net.netty.webSocket.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.ssl.SslContext;

public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private final ChannelGroup group;
    private final SslContext sslContext;
    private static final String WEBSOCKET_PATH = "/webSocket";

    public WebSocketServerInitializer(SslContext sslContext, ChannelGroup group) {
        this.sslContext = sslContext;
        this.group = group;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslContext != null) {
            pipeline.addLast(sslContext.newHandler(ch.alloc()));
        }
        // http支持
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        // Netty提供: 支持WebSocket应答数据压缩传输
        pipeline.addLast(new WebSocketServerCompressionHandler());
        // Netty提供: 专门用于处理WebSocket通信(协议升级、握手、以及相关的通信控制)
        pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH,
                null, true));
        pipeline.addLast(new ProcessWsIndexPageHandler(WEBSOCKET_PATH));
        pipeline.addLast(new ProcessWsFrameHandler(group));
    }
}
