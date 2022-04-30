package org.hc.learning.net.rpc.server;

import org.hc.learning.net.bio.Server;
import org.hc.learning.net.rpc.server.service.impl.HelloWorldServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class EasyRpcServer extends Server {

    private static EasyRpcRegistryContext context = null;

    public static void main(String[] args) throws IOException {
        /*
        register service
         */
        context = new EasyRpcRegistryContext(new Class[]{HelloWorldServiceImpl.class});

        /*
        start server
         */
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(10001));
        System.out.println("easy rpc server start ......");
        while (true) {
            service.execute(new EasyRpcServerTask(server.accept()));
        }
    }

    private static class EasyRpcServerTask implements Runnable {
        private Socket socket = null;

        public EasyRpcServerTask(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            SocketAddress address = socket.getRemoteSocketAddress();
            System.out.println("receive connection from " + address);
            try (
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())
            ) {
                /*
                read object
                 */
                String clazzName = (String) inputStream.readObject();
                String methodName = (String) inputStream.readObject();
                Class[] paramTypes = (Class[]) inputStream.readObject();
                Object[] args = (Object[]) inputStream.readObject();
                System.out.println("remote socket call " + clazzName + "." + methodName + "." + paramTypes + "." + args);
                Object instance = context.getInstance(clazzName);
                Object result = null;
                if (instance == null) {
                    result = "not found service:" + clazzName;
                } else {
                    Method method = instance.getClass().getMethod(methodName, paramTypes);
                    result = method.invoke(instance, args);
                }
                System.out.println("return result: " + result);
                /*
                write object
                 */
                outputStream.writeObject(result);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("close the connection " + address);
            }
        }
    }
}
