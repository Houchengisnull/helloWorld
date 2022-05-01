package org.hc.learning.net.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class NettyClient {

    private final int port;
    private final String host;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start(){
        /*  线程组 */
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class) // 指定使用NIO模型进行网络通信
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new NettyClientHandle());
            ChannelFuture future = bootstrap.connect().sync(); // 阻塞程序，直到连接完成
            System.out.println("netty client connect finish.");
            future.channel().closeFuture().sync(); // 阻塞程序 直到通道关闭
            System.out.println("netty client channel closed.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("netty client group closed.");
            try {
                group.shutdownGracefully().sync(); // 优雅关闭线程组
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        new NettyClient("127.0.0.1", 9999).start();
    }
}
