package org.hc.learning.net.netty.http.proxy;

import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpProxyFrontendHandler extends ChannelInboundHandlerAdapter {

    /**
     * Backend Bootstrap
     */
    private volatile Channel frontendChannel;
    private volatile Channel backendChannel;
    private String remoteIp;
    private int remotePort;


    public HttpProxyFrontendHandler(String remoteIp, int remotePort) {
        this.remoteIp = remoteIp;
        this.remotePort = remotePort;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        frontendChannel = ctx.channel();
        String frontendChannelId = frontendChannel.id().asShortText();
        boolean frontendChannelAutoRead = frontendChannel.config().isAutoRead();
        log.info("Frontend channel[{}] is auto read:{}", frontendChannelId, frontendChannelAutoRead);
        if (frontendChannelAutoRead) {
            frontendChannel.config().setAutoRead(false);
        }

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(frontendChannel.eventLoop())
            .channel(frontendChannel.getClass())
            /*.option(ChannelOption.AUTO_READ, false)*/
            .remoteAddress(remoteIp, remotePort)
            .handler(new ChannelInitializer() {
                @Override
                protected void initChannel(Channel ch) {
                    ch.pipeline().addLast(new HttpClientCodec());
                    ch.pipeline().addLast("aggregator", new HttpObjectAggregator(512 * 1024));
                    ch.pipeline().addLast("decompressor", new HttpContentDecompressor());
                    ch.pipeline().addLast(new HttpProxyBackendHandler(frontendChannel));
                }
            });

        try {
            ChannelFuture future = bootstrap.connect();
            future.addListener((ChannelFutureListener) f->{
                if (f.isSuccess()) {
                    // connection is successful. enable read.
                    frontendChannel.read();
                } else {
                    frontendChannel.close();
                }
            });
            backendChannel = future.channel();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ctx.close().addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest httpRequest = (FullHttpRequest) msg;
        String uri = httpRequest.uri();
        HttpHeaders headers = httpRequest.headers();
        log.debug("\nReceive Http Request:" +
                "\nHttp Request Uri:{}" +
                "\nHttp Request Headers:{}" +
                "\nHttp Request Body:{}"
                , uri
                , headers
                , httpRequest.content().toString(CharsetUtil.UTF_8));

        String backendChannelId = backendChannel.id().asShortText();

        if (backendChannel.isActive()) {
            log.info("Backend channel[{}] write and flush", backendChannelId);
            backendChannel.writeAndFlush(httpRequest);
        } else {
            JSONObject unknownError = new JSONObject();
            unknownError.put("message", "Unknown Error");
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1
                    , HttpResponseStatus.BAD_GATEWAY
                    , Unpooled.copiedBuffer(unknownError.toJSONString(true), CharsetUtil.UTF_8));
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // close backend channel
        log.info("Frontend channel[{}] is inactive", ctx.channel().id().asShortText());
        closeChannel(backendChannel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        // close backend channel
        closeChannel(backendChannel);
    }

    private void closeChannel(Channel channel) {
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
