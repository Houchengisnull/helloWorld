package org.hc.learning.net.netty.粘包半包;

import io.netty.channel.ChannelInitializer;
import org.hc.learning.net.netty.util.BootstrapUtil;

public class LineBasedClient {

    public static void main(String[] args) {
        ChannelInitializer initializer = new LineBasedClientInitializer();
        BootstrapUtil.connect("localhost", initializer);
    }

}
