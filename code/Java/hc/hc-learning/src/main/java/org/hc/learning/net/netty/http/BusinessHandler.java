package org.hc.learning.net.netty.http;

import cn.hutool.core.util.StrUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusinessHandler extends ChannelInboundHandlerAdapter {

    private void send(ChannelHandlerContext ctx, String context, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1
                , status
                , Unpooled.copiedBuffer(context, CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String result = StrUtil.EMPTY;
        FullHttpRequest httpRequest = (FullHttpRequest) msg;
        log.info("http headers:{}", httpRequest.headers());
        try {
            String path = httpRequest.uri();
            String body = httpRequest.content().toString(CharsetUtil.UTF_8);
            HttpMethod method=httpRequest.method();
            if (!"/hello".equalsIgnoreCase(path)) {
                result = StrUtil.format("非法路径:{}", path);
                send(ctx, result, HttpResponseStatus.NOT_FOUND);
                return;
            }

            if (HttpMethod.GET.equals(method)) {
                log.info("body:{}", body);
                result = "Hello world";
                send(ctx, result, HttpResponseStatus.OK);
                return;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            httpRequest.release();
        }
    }
}