package org.hc.learning.thread.notify.bure.notify.bure.修正;

public class App {

    static Bure bure = new Bure();

    static class Shot implements Runnable {

        @Override
        public void run() {
            while (true) {
                bure.pop();
            }
        }
    }

    static class Push implements Runnable {

        @Override
        public void run() {
            while (true) {
                bure.push();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Shot()).start();
        new Thread(new Push()).start();
    }
}
