package org.hc.learning.net.bio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    protected static ExecutorService service
            = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(10001));

        System.out.println("server start ......");

        while (true) {
            /*Socket socket = server.accept();
            ServerTask task = new ServerTask(socket);
            new Thread(task).start();*/
            service.execute(new ServerTask(server.accept()));
        }
    }

    private static class ServerTask implements Runnable {

        private Socket socket = null;

        public ServerTask(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())
            ) {
                String message = inputStream.readUTF();
                System.out.println("accept client message:" + message);

                outputStream.writeUTF("have receive: " + message);
                outputStream.flush(); // 刷新缓冲区
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
