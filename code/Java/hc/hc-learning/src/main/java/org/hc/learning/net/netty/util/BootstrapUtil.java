package org.hc.learning.net.netty.util;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

@Slf4j
public class BootstrapUtil {
    public static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

    public static void bind(ChannelInitializer initializer) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        bind(group, initializer);
    }

    public static void bind(NioEventLoopGroup parentGroup, NioEventLoopGroup workGroup, ChannelHandler channelHandler) throws CertificateException, SSLException, InterruptedException {
        final SslContext sslCtx = ServerUtil.buildSslContext();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(7)) // 设置接收缓冲区大小
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc()));
                            }
                            p.addLast(channelHandler);
                        }
                    });

            ChannelFuture f = b.bind(PORT).sync();
            ((ChannelFuture) f).channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally{
            parentGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void bind(NioEventLoopGroup group, ChannelInitializer initializer) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(initializer);

        try {
            ChannelFuture future = bootstrap.bind(PORT).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void bindHttpServer(NioEventLoopGroup parentGroup, NioEventLoopGroup workGroup, ChannelHandler channelHandler) {
        try {
            ServerBootstrap b = new ServerBootstrap();

            b.group(parentGroup, workGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(channelHandler);

            ChannelFuture f = b.bind(PORT).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally {
            parentGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void connect(String host, ChannelInitializer initializer) {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(host, PORT)
                .handler(initializer);

        try {
            ChannelFuture future = bootstrap.connect().sync();
            log.info("connect successful. host:{}, port:{}", host, PORT);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void sendWithUDP(String hostname, int port, ChannelHandler channelHandler, String message, int timeout) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .handler(channelHandler);
        Channel channel = null;
        try {
            // bind 0 意味着由系统随机分配端口号
            channel = bootstrap.bind(0).sync().channel();
            channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8), new InetSocketAddress(hostname, port))).sync();

            if (!channel.closeFuture().await(timeout)) {
                log.error("timeout");
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void answerWithUDP(int port, ChannelHandler channelHandler) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .handler(channelHandler);
        Channel channel = null;
        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("answer with UDP start.");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
