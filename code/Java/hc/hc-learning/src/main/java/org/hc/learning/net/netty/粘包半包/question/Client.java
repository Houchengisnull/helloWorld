package org.hc.learning.net.netty.粘包半包.question;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import java.net.InetSocketAddress;

@Slf4j
public class Client {

    @SneakyThrows
    public static void main(String[] args) {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                    }
                })
                .connect(new InetSocketAddress("localhost", 8899));

        Channel channel = channelFuture.sync().channel();

        // 12 bytes
        byte[] bytes = "Hello world.This world is good.I like living in the world.Why? Because I have own much much money.You know? Money is the most powerful thing than everything.OK, Have I writen words that more than 64?".getBytes(CharsetUtil.UTF_8);

        channel.writeAndFlush(ByteBufAllocator.DEFAULT.buffer().writeBytes(bytes));

    }

}
