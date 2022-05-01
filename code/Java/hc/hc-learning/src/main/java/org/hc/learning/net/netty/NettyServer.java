package org.hc.learning.net.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        int port = 9999;
        NettyServer nettyServer = new NettyServer(port);
        System.out.println("netty server start ...");
        nettyServer.start();
        System.out.println("netty server close ...");
    }

    public void start() {
        final NettyServerHandler serverHandler = new NettyServerHandler();
        /*线程组*/
        EventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {
            serverBootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    /* 服务端每接收一个连接请求就会创建一个channel（即socket）
                    * childHandler为每个channel指定一个handler
                    * */
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });
            /**
             * 异步绑定服务器，阻塞直到完成
             */
            ChannelFuture future = serverBootstrap.bind().sync();
            System.out.println("netty sever bind finish.");
            /**
             * 阻塞直到服务器channel关闭
             */
            future.channel().closeFuture().sync();
            System.out.println("netty sever channel closed.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
