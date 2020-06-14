package org.hc.learning.thread.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class MyAtomicInt {

    private AtomicInteger value = new AtomicInteger(0);

    /**
     * 自增 atomicI++
     */
    public void increment() {
        for(;;) {
            int count = getCount();
            boolean suc = compareAndSet(count, ++count);
            if (suc) {
                break;
            }
        }
        /*value.incrementAndGet();*/
    }

    public int getCount() {
        return value.get();
    }

    public boolean compareAndSet(Integer oldValue, Integer newValue) {
        return value.compareAndSet(oldValue,newValue);

    }

    public static void main(String[] args) throws InterruptedException {
        MyAtomicInt atomicInt = new MyAtomicInt();
        for(int i=0;i<10;i++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<1000;i++){
                        atomicInt.increment();
                    }
                }
            });
            thread.start();
            /**
             * 不应该用 thread.join()方法
             * 因为join方法是通过synchronized实现的 将以当前线程作为锁资源
             * 注意 thread.join()当前线程为 Main线程
             * 故导致所有线程同步执行
             */
            /*thread.join();*/
        }
        Thread.sleep(10);
        System.out.println(atomicInt.getCount());
    }
}
