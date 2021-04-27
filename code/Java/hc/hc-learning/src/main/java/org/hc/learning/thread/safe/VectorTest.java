package org.hc.learning.thread.safe;

import java.util.Vector;

public class VectorTest {

    private final static Vector<Integer> vector = new Vector();

    public static void main(String[] args) {

        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
                System.out.println("当前线程" + Thread.currentThread().getName() + " add vector[" + i + "] = " + vector.get(i) + "\t");

            }

            Thread removeThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        try {
                            System.out.println("当前线程" + Thread.currentThread().getName() + " remove vector[" + i + "] = " + vector.get(i) + "\t");
                            vector.remove(i);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();
                            System.err.print("结束线程: " + Thread.currentThread().getName() + " " + i);
                            System.exit(1);
                        }

                    }
                }
            });

            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("当前线程" + Thread.currentThread().getName() + " print vector[" + i + "] = " + vector.get(i) + "\t");
                    }
                }
            });

            removeThread.start();
            printThread.start();

            while (Thread.activeCount() >= 20);
        }


    }

}
