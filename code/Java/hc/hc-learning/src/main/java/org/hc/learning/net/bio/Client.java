package org.hc.learning.net.bio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 10001);
        try (
            Socket socket = new Socket();
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream())
        ) {
            socket.connect(addr);
            output.writeUTF("Mark");
            output.flush();
            System.out.println(input.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
