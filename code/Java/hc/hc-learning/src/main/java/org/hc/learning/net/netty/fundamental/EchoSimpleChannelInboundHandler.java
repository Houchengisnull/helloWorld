package org.hc.learning.net.netty.fundamental;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoSimpleChannelInboundHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("receive message:{}", msg);
        ctx.write(msg); //echo
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("read buffer finish.");
        ctx.flush();
        /*super.channelReadComplete(ctx);*/
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("receive message:{}", ((ByteBuf) msg).toString(CharsetUtil.UTF_8));
        super.channelRead(ctx, msg);
    }
}
