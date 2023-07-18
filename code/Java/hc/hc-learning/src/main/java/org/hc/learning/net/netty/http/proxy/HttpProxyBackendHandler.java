package org.hc.learning.net.netty.http.proxy;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
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
        log.debug("\nReceive Http Response: {}", httpResponse.content().toString(CharsetUtil.UTF_8));
        frontendChannel.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("Backend channel[{}] is active", ctx.channel().id().asShortText());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("Backend channel[{}] is inactive", ctx.channel().id().asShortText());
        closeChannel(ctx.channel());
    }

    private void closeChannel(Channel channel) {
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
