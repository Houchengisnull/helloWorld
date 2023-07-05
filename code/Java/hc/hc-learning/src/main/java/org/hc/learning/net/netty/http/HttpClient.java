package org.hc.learning.net.netty.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.SneakyThrows;
import org.hc.learning.net.netty.util.BootstrapUtil;
import java.net.URI;

public class HttpClient {

    @SneakyThrows
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new HttpClientCodec());
                ch.pipeline().addLast("aggregator", new HttpObjectAggregator(512 * 1024));
                ch.pipeline().addLast("decompressor", new HttpContentDecompressor());
                ch.pipeline().addLast(new HttpClientInboundHandler());
            }
        });

        String host = "127.0.0.1";
        ChannelFuture future = bootstrap.connect(host, BootstrapUtil.PORT);
        URI uri = new URI("/hello");
        String msg = "Hello";
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes(CharsetUtil.UTF_8)));

        request.headers().set(HttpHeaderNames.HOST, host);
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

        future.channel().write(request);
        future.channel().flush();
        future.channel().closeFuture().sync();

        group.shutdownGracefully();
    }

}
