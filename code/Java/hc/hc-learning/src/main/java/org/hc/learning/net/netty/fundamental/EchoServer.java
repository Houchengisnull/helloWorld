package org.hc.learning.net.netty.fundamental;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.ssl.SslContext;
import org.hc.learning.net.netty.util.BootstrapUtil;
import org.hc.learning.net.netty.util.ServerUtil;
import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

public class EchoServer {

    public static void main(String[] args) throws CertificateException, SSLException, InterruptedException {
        final SslContext sslCtx = ServerUtil.buildSslContext();

        NioEventLoopGroup parentGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        EchoServerHandler handler = new EchoServerHandler();
        BootstrapUtil.bind(parentGroup, workGroup, handler);
    }

}
