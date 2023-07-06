package org.hc.learning.net.netty.serializable.msgpack;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;
import org.hc.learning.net.netty.util.BootstrapUtil;

public class MsgPackEchoClient {

    public static void main(String[] args) {
        BootstrapUtil.connect("127.0.0.1", new ChannelInitializerImp());
    }

    private static class ChannelInitializerImp extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {

            /*设置报文长度，避免粘包半包*/
            ch.pipeline().addLast("frameEncode",
                    new LengthFieldPrepender(2));

            /*对发送数据的序列化*/
            ch.pipeline().addLast("MsaPack-Encoder",new MsgPackEncode());

            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));

            //产生实体类，交给编码器进行序列化
            ch.pipeline().addLast(new MsgPackClientHandler(5));
        }
    }

}
