package org.hc.learning.net.netty.serializable.msgpack;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.hc.learning.net.netty.util.BootstrapUtil;

public class MsgPackEchoServer {

    public static void main(String[] args) {
        BootstrapUtil.bind(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                ch.pipeline().addLast("frameDecoder",
                        new LengthFieldBasedFrameDecoder(65535,
                                0,2,0,
                                2));
                // 反序列化
                ch.pipeline().addLast("MsgPack-Decoder",new MsgPackDecoder());
                // 发序列化实体处理
                ch.pipeline().addLast(new MsgPackServerHandler());
            }
        });
    }

}
