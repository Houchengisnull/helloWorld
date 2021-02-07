package org.hc.learning.thread.题目.顺序执行;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Setter
@Getter
@Slf4j
public class StatePrintTask implements Runnable{
    /**
     * state实际表示顺序
     */
    private int state;
    private static int size = 0;
    private static int times = 10;
    private static /*volatile*/ Integer currentState = 0;
    private static AtomicInteger atomCurrentState = new AtomicInteger(0);
    private static byte[] lock = new byte[0];


    public StatePrintTask(int state){
        this.state = state;
        // 统计总数
        this.size++;
    }

    @Override
    public void run() {
        for (int i = 0; i < times; i++) {
            /*printThreadName();*/
            /*printThreadNameAtom();*/
            printThreadNameSync();
        }
    }

    /**
     *
     */
    @SneakyThrows
    public void printThreadName(){
        /**
         * 类似自旋锁
         */
        while (currentState % size != state){
            Thread.sleep(10); // 如果不休眠一定要对用 volatile 声明
        }
        /**
         * 关键在于只有持有锁的线程可以修改currentState
         */
        synchronized (currentState) {
            /*log.debug(Thread.currentThread().getName());*/
            System.out.print(Thread.currentThread().getName() + (currentState%size + 1 == size? "\n": ""));
            currentState++;
        }
    }

    public void printThreadNameAtom() {
        int t;
        while ((t = atomCurrentState.get()) % size != state) {}
        System.out.print(Thread.currentThread().getName() + (t%size + 1 == size? "\n": ""));
        atomCurrentState.addAndGet(1);
    }

    public void printThreadNameSync(){
            synchronized (lock) {
                while (currentState%3 != state) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.print(Thread.currentThread().getName() + (currentState%size + 1 == size? "\n": ""));
                currentState++;
                lock.notifyAll();
            }
    }
}
