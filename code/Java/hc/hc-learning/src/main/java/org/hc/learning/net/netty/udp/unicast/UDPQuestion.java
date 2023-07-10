package org.hc.learning.net.netty.udp.unicast;

import org.hc.learning.net.netty.util.BootstrapUtil;

public class UDPQuestion {

    public static void main(String[] args) {
        BootstrapUtil.sendWithUDP("127.0.0.1", 8006, new UDPQuestionHandler(), "Hello", 15000);
    }

}
