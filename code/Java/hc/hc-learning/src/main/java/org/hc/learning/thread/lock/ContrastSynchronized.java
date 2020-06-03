package org.hc.learning.thread.lock;

public class ContrastSynchronized implements Runnable{

    static byte[] lock = new byte[0];

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ContrastSynchronized());
        Thread thread2 = new Thread(new ContrastSynchronized());
        thread.start();
        thread2.start();

        // 线程2未理会中,一直处于阻塞状态
        thread2.interrupt();
        Thread.sleep(10000);
        System.out.println(thread.getState());
        System.out.println(thread2.getState());
    }

    @Override
    public void run() {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + " 获取锁执行");
            long startTime = System.currentTimeMillis();
            for(    ;     ;) {
                if(System.currentTimeMillis() - startTime >= Integer.MAX_VALUE)
                    break;
                //插入数据
            }
            System.out.println(Thread.currentThread().getName() + " 执行结束 释放锁");
        }
    }
}
