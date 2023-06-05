package org.hc.learning.net.netty.embedded;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            while (byteBuf.readableBytes() >= 4) { // 检查是否有足够的字节用于编码 int为4个字节
                int value = Math.abs(byteBuf.readInt());
                list.add(value);
            }
    }

}
