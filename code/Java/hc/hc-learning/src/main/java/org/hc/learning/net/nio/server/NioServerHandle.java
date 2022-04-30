package org.hc.learning.net.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServerHandle implements Runnable {

    private Selector selector;
    private ServerSocketChannel channel;
    private volatile boolean isStart;

    public NioServerHandle(int port) {
        try {
            selector = Selector.open();
            channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress(9999);
            channel.bind(address);
            // 注册连接事件
            channel.register(selector, SelectionKey.OP_ACCEPT);

            isStart = true;
            System.out.println("nio server start..." + address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isStart = false;
    }

    @Override
    public void run() {
        while (isStart) {
            try {
                // 阻塞方法: 只有当至少一个注册的时间发生时才会继续执行
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    iterator.remove(); // 必须显式remove, 否则事件无法被清理，始终保留在selectedKeys中
                    handleInput(next);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                SocketChannel channel = serverChannel.accept();
                System.out.println("建立连接: " + channel.getRemoteAddress());
                channel.configureBlocking(false);
                channel.register(selector, SelectionKey.OP_READ);
            }
            // 读消息
            if (key.isReadable()) {
                System.out.println("数据准备完成");
                SocketChannel channel = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int read = channel.read(buffer); // 从直接内存拷贝数据到JVM内存中
                if (read > 0) {
                    // 切换为读取模式，并设置limit
                    buffer.flip();
                    //
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String message = new String(bytes, "UTF8");
                    System.out.println("服务器收到消息:" + message);

                    // ack
                    String ackContent = "hello " + message;
                    byte[] ackByte = ackContent.getBytes();
                    ByteBuffer ackBuffer = ByteBuffer.allocate(ackByte.length);
                    ackBuffer.put(ackByte);
                    ackBuffer.flip();
                    /* 一次性写完的情况 */
                    /*channel.write(ackBuffer);*/
                    /* 一次性写不完的情况 */
                    channel.register(selector, SelectionKey.OP_WRITE|SelectionKey.OP_READ, ackBuffer);
                } else if (read < 0) { // 链路已关闭释放资源
                    key.cancel(); // 取消当前事件
                    channel.close();
                }
            }

            if(key.isWritable()){
                System.out.println("写入数据.....");
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer att = (ByteBuffer)key.attachment();
                if(att.hasRemaining()){
                    int count = sc.write(att);
                    System.out.println("write:"+count+ "byte [" + new String(att.array()) + "],hasR:"
                            +att.hasRemaining());
                }else{
                    System.out.println("write hasn't remaining");
                    key.interestOps(SelectionKey.OP_READ); // 当前selector仅关注读事件
                }
            }
        }
    }
}
