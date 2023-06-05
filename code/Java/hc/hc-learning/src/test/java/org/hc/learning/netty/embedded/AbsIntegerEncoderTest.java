package org.hc.learning.netty.embedded;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.hc.learning.net.netty.embedded.AbsIntegerEncoder;
import org.hc.learning.test.TestCase;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

@Slf4j
public class AbsIntegerEncoderTest extends TestCase {

    @Test
    public void testEncoded(){
        // 1 创建一个ByteBuf
        ByteBuf byteBuf = Unpooled.buffer();
        for (int i = 0; i < 10; i++) {
            byteBuf.writeInt(-i);
        }

        // 2 创建EmbeddedChannel
        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        // 3 写入
        assertTrue(channel.writeOutbound(byteBuf));
        // 4 将channel标记为finish
        assertTrue(channel.finish());

        // 5 read
        for (int i = 0; i < 10; i++) {
            int x = channel.readOutbound();
            assertEquals(i, x);
        }
        assertNull(channel.readOutbound());
    }

}
