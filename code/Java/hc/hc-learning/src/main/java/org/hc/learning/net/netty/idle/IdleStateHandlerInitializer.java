package org.hc.learning.net.netty.idle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * - IdleStateHandler在空间时间太长时, 会触发一个IdleStateEvent
 * - 重写ChannelInboundHandler的userEventTriggered()用以处理IdleStateEvent
 *
 * 超时Handler:
 * - ReadTimeoutHandler 指定时间未收到任何入站数据
 * - WriteTimeoutHandler 指定时间没有出站数据写入
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        // 1. IdleStateHandler在被触发时, 发送一个IdleStateEvent事件
        ch.pipeline().addLast(new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS));
        ch.pipeline().addLast(new HeartbeatHandler());
    }

    public static final class HeartbeatHandler extends ChannelInboundHandlerAdapter {
        private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.ISO_8859_1));

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                // 2. 发送心跳信息, 并在发送失败时关闭该连接
                ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }else {
                // 不是IdleStateEvent事件, 所以将它传递给下一个ChannelInboundHandler
                super.userEventTriggered(ctx, evt);
            }
        }
    }
}
