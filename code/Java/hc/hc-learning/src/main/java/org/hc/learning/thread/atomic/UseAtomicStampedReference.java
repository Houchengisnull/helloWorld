package org.hc.learning.thread.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 版本戳的原子操作类
 */
public class UseAtomicStampedReference {
    /**
     * 初始值
     * 初始stamp
     */
    static AtomicStampedReference<String> asr = new AtomicStampedReference("Houc", 0);

    public static void main(String[] args) throws InterruptedException {
        final int oldStamp = asr.getStamp();
        final String oldReference = asr.getReference();
        System.out.println(oldReference + " ================ " + oldStamp);

        Thread rightStampThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()
                        + ": 当前变量值:" + oldReference
                        + "-当前版本戳:" + oldStamp + "-"
                        + asr.compareAndSet(oldReference, oldReference + " Java", oldStamp, oldStamp + 1));
            }
        });

        Thread errorStampThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String reference = asr.getReference();
                System.out.println(Thread.currentThread().getName()
                        +":当前变量值：" + reference
                        + "-当前版本戳：" + asr.getStamp() + "-"
                        + asr.compareAndSet(reference,
                        reference + "+C", oldStamp,
                        oldStamp + 1));
            }
        });
        rightStampThread.start();
        rightStampThread.join();
        errorStampThread.start();
        errorStampThread.join();

        System.out.println(asr.getReference()+"============"+asr.getStamp());
    }
}
