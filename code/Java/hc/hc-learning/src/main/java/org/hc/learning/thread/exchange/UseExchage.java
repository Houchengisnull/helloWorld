package org.hc.learning.thread.exchange;

import java.util.concurrent.Exchanger;

public class UseExchage {

    static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message = "AAA";
                try {
                    message = exchanger.exchange(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " receive message:"+ message);
            }
        }, "A").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                String message = "BBB";
                try {
                    message = exchanger.exchange(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " receive message:"+ message);
            }
        }, "B").start();

    }
}
