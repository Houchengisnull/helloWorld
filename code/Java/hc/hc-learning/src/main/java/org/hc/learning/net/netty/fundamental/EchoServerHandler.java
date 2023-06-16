package org.hc.learning.net.netty.fundamental;

import com.alibaba.druid.sql.visitor.functions.Char;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private AtomicInteger i = new AtomicInteger(0);

    /**
     * 该方法在进程读取完输入缓冲区的数据后触发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        log.info("server receive:{}", in.toString(CharsetUtil.UTF_8));
        ctx.write(in);
        ctx.flush();
    }

    /**
     * 该方法在进程每次读取Buffer后触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("read buffer finish");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
