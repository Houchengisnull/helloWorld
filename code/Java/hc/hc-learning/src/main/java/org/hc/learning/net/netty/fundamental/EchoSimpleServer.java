package org.hc.learning.net.netty.fundamental;

import io.netty.channel.nio.NioEventLoopGroup;
import org.hc.learning.net.netty.util.BootstrapUtil;
import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

public class EchoSimpleServer  {

    public static void main(String[] args) throws CertificateException, SSLException, InterruptedException {
        NioEventLoopGroup parentGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        EchoSimpleChannelInboundHandler handler = new EchoSimpleChannelInboundHandler();
        BootstrapUtil.bootstrap(parentGroup, workGroup, handler);
    }

}
