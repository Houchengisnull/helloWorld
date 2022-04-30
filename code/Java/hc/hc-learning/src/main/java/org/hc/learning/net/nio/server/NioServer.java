package org.hc.learning.net.nio.server;

public class NioServer {

    public static void main(String[] args) {
        new Thread(new NioServerHandle(9999), "NioServer").start();
    }

}
