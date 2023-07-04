package org.hc.learning.net.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import lombok.SneakyThrows;

public class HttpsServerHandlerInit extends ChannelInitializer<SocketChannel> {

    private SslContext sslContext;

    @SneakyThrows
    public HttpsServerHandlerInit() {
        SelfSignedCertificate certificate = new SelfSignedCertificate();
        sslContext = SslContextBuilder.forServer(certificate.certificate(),
                certificate.privateKey()).build();
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(sslContext.newHandler(ch.alloc()));
        p.addLast("encoder",new HttpResponseEncoder());
        p.addLast("decoder",new HttpRequestDecoder());
        //ph.addLast(new HttpServerCodec());
        p.addLast("aggregator", new HttpObjectAggregator(10*1024*1024));
        p.addLast("compressor",new HttpContentCompressor()); // 压缩
        p.addLast("handler", new BusinessHandler());
    }
}
