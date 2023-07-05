package org.hc.learning.net.netty.粘包半包.lengthfieldbased;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.hc.learning.test.TestCase;
import org.junit.Test;

@Slf4j
public class LengthFieldBasedDecoderTest extends TestCase {

    @Test
    public void lengthFieldBasedTest() {
        EmbeddedChannel channel = new EmbeddedChannel(new LoggingHandler(LogLevel.DEBUG),
                new LengthFieldBasedFrameDecoder(100, 2, 4, -8, 7),
                new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        ByteBuf buf = ((ByteBuf) msg);
                        String content = buf.toString(CharsetUtil.UTF_8);
                        System.out.println(content);
                    }
                });
        channel.writeInbound(ByteBufAllocator.DEFAULT.buffer().writeBytes(frame()));
    }

    private static ByteBuf frame() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeShort(11);
        buffer.writeInt(20);
        buffer.writeByte(2);
        buffer.writeBytes("hello world".getBytes(CharsetUtil.UTF_8));
        return buffer;
    }
}
