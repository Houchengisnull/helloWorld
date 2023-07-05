package org.hc.learning.net.netty.粘包半包;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.SystemProperties;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class LineBasedClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        log.info("accept:{} and the counter is {}", msg.toString(CharsetUtil.UTF_8), counter.incrementAndGet());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf msg = null;
        String lineBased = System.getProperty("line.separator");
        String request = "Hello world.This world is not good" + lineBased;
        for (int i = 0; i < 100; i++) {
            Thread.sleep(500);
            log.info("prepare send message:{}", request);
            msg = Unpooled.buffer(request.length());
            msg.writeBytes(request.getBytes(CharsetUtil.UTF_8));
            ctx.writeAndFlush(msg);
        }
    }
}
