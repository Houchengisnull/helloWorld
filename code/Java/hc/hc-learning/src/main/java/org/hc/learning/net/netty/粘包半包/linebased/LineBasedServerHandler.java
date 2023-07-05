package org.hc.learning.net.netty.粘包半包.linebased;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ChannelHandler.Sharable
public class LineBasedServerHandler extends ChannelInboundHandlerAdapter {

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        String message = in.toString(CharsetUtil.UTF_8);

        String response = "[Line Base Server]" + message + ".The counter is " + counter.incrementAndGet();
        log.info("{}", response);
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
    }
}
