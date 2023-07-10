package org.hc.learning.net.netty.udp.unicast;

import org.hc.learning.net.netty.util.BootstrapUtil;

public class UDPAnswer {

    public static void main(String[] args) {
        BootstrapUtil.answerWithUDP(8006, new UDPAnswerHandler());
    }
}
