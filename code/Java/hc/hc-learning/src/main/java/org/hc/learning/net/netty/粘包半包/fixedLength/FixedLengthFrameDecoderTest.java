package org.hc.learning.net.netty.粘包半包.fixedLength;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.hc.learning.test.TestCase;
import org.junit.Test;

@Slf4j
public class FixedLengthFrameDecoderTest extends TestCase {

    @Test
    public void fixedLengthTest() {
        EmbeddedChannel channel = new EmbeddedChannel(new LoggingHandler(LogLevel.INFO), new FixedLengthFrameDecoder(5),
                new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        ByteBuf buf = ((ByteBuf) msg);
                        String content = buf.toString(CharsetUtil.UTF_8);
                        log.info(content);
                    }
                });
        channel.writeInbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("hello\nworld\nwelcome\r".getBytes(CharsetUtil.UTF_8)));
    }

}
