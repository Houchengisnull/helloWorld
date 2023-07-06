package org.hc.learning.net.netty.serializable.protobuf;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.hc.learning.net.netty.util.BootstrapUtil;;

public class ProtoBufServer {

    public static void main(String[] args) {
        BootstrapUtil.bind(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                // ProtobufVarint32FrameDecoder 分离数据帧
                pipeline.addLast(new ProtobufVarint32FrameDecoder());
                // ProtobufDecoder 字节解码为pojo
                pipeline.addLast(new ProtobufDecoder(PersonProto.Person.getDefaultInstance()));
                pipeline.addLast(new ProtoBufServerHandler());
            }
        });
    }

}
