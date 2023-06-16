package org.hc.learning.net.netty.fundamental;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    public EchoClientHandler() {}

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("write and flush");
        ByteBuf firstMessage = Unpooled.buffer(5);
        firstMessage.writeBytes("Hello world.".getBytes(CharsetUtil.UTF_8));
        ctx.write(firstMessage);

        ByteBuf secondMessage = Unpooled.buffer(5);
        secondMessage.writeBytes("Hello Netty.".getBytes(CharsetUtil.UTF_8));
        ctx.write(secondMessage);

        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("echo :{}", ((ByteBuf) msg).toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // close the connection when an exception is raised
        cause.printStackTrace();
        ctx.close();
    }
}
