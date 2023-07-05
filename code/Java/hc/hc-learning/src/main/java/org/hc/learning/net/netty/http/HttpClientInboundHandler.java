package org.hc.learning.net.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpResponse httpResponse = (FullHttpResponse)msg;
        System.out.println(httpResponse.headers());

        ByteBuf buf = httpResponse.content();
        System.out.println(buf.toString(CharsetUtil.UTF_8));
        httpResponse.release();
    }

}
