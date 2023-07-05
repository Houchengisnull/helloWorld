package org.hc.learning.net.netty.粘包半包;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import lombok.SneakyThrows;
import org.hc.learning.net.netty.util.BootstrapUtil;

public class LineBasedServer {

    @SneakyThrows
    public static void main(String[] args) {
        NioEventLoopGroup parentGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ChannelInitializer<SocketChannel> initializer = new LineBasedServerInitializer();
        BootstrapUtil.bind(parentGroup, workGroup, initializer);
    }


}
