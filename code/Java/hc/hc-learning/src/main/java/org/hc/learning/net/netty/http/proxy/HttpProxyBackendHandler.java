package org.hc.learning.net.netty.http.proxy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpProxyBackendHandler extends ChannelInboundHandlerAdapter {

    private Channel frontendChannel;

    public HttpProxyBackendHandler(Channel channel) {
        frontendChannel = channel;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        FullHttpResponse httpResponse = (FullHttpResponse) msg;
        log.info("\nReceive Http Response: {}", httpResponse.content().toString(CharsetUtil.UTF_8));
        frontendChannel.writeAndFlush(httpResponse);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("Backend channel[{}] is active", ctx.channel().id().asShortText());
    }
}
