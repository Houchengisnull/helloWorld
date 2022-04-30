package org.hc.learning.net.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioClientHandle implements Runnable{

    private String host;
    private int port;
    private volatile boolean started;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean isStart;

    public NioClientHandle(String ip, int port) {
        this.host = ip;
        this.port = port;
        try {
            /*创建选择器*/
            this.selector = Selector.open();
            /*打开监听通道*/
            socketChannel = SocketChannel.open();
            /*如果为 true，则此通道将被置于阻塞模式；
             * 如果为 false，则此通道将被置于非阻塞模式
             * 缺省为true*/
            socketChannel.configureBlocking(false);
            started = true;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    public void stop(){
        started = false;
    }


    @Override
    public void run() {
        //连接服务器
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        /*循环遍历selector*/
        while(started){
            try {
                /*阻塞方法,当至少一个注册的事件发生的时候就会继续*/
                selector.select();
                /*获取当前有哪些事件可以使用*/
                Set<SelectionKey> keys = selector.selectedKeys();
                /*转换为迭代器*/
                Iterator<SelectionKey> it = keys.iterator();
                SelectionKey key = null;
                while(it.hasNext()){
                    key = it.next();
                    /*我们必须首先将处理过的 SelectionKey 从选定的键集合中删除。
                    如果我们没有删除处理过的键，那么它仍然会在事件集合中以一个激活
                    的键出现，这会导致我们尝试再次处理它。*/
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if(key!=null){
                            key.cancel();
                            if(key.channel()!=null){
                                key.channel().close();
                            }
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        if(selector!=null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /*具体的事件处理方法*/
    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            /*获得关心当前事件的channel*/
            SocketChannel sc =(SocketChannel)key.channel();
            /*处理连接就绪事件
             * 但是三次握手未必就成功了，所以需要等待握手完成和判断握手是否成功*/
            if(key.isConnectable()){
                /*finishConnect的主要作用就是确认通道连接已建立，
                方便后续IO操作（读写）不会因连接没建立而
                导致NotYetConnectedException异常。*/
                if(sc.finishConnect()){
                    /*连接既然已经建立，当然就需要注册读事件，
                    写事件一般是不需要注册的。*/
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }else System.exit(-1);
            }

            /*处理读事件，也就是当前有数据可读*/
            if(key.isReadable()){
                /*创建ByteBuffer，并开辟一个1k的缓冲区*/
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                /*将通道的数据读取到缓冲区，read方法返回读取到的字节数*/
                int readBytes = sc.read(buffer);
                if(readBytes>0){
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String result = new String(bytes,"UTF-8");
                    System.out.println("客户端收到消息："+result);
                }
                /*链路已经关闭，释放资源*/
                else if(readBytes<0){
                    key.cancel();
                    sc.close();
                }

            }
        }
    }

    /*进行连接*/
    private void doConnect() throws IOException {
        /*如果此通道处于非阻塞模式，则调用此方法将启动非阻塞连接操作。
        如果连接马上建立成功，则此方法返回true。
        否则，此方法返回false，
        因此我们必须关注连接就绪事件，
        并通过调用finishConnect方法完成连接操作。*/
        if(socketChannel.connect(new InetSocketAddress(host,port))){
            /*连接成功，关注读事件*/
            socketChannel.register(selector,SelectionKey.OP_READ);
        }
        else{
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
        }
    }

    /*写数据对外暴露的API*/
    public void sendMsg(String msg) throws IOException {
        doWrite(socketChannel,msg);
    }

    private void doWrite(SocketChannel sc,String request) throws IOException {
        byte[] bytes = request.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip(); // 将写模式切换为读模式，令sc对象读取buffer
        sc.write(writeBuffer);
    }
}
