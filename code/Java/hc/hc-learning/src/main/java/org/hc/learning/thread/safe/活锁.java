package org.hc.learning.thread.safe;

import lombok.SneakyThrows;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class 活锁 {
    private static final long 厨子A切菜时间 = 500l;
    private static final long 厨子B切菜时间 = 1000l;

    private static final long 休息时间 = 10l;

    private static Lock 菜刀 = new ReentrantLock();
    private static Lock 案板 = new ReentrantLock();
    public static void main(String[] args) {
        Thread 厨子A = new Thread(new 厨子A的工作流程(), "厨子A");
        Thread 厨子B = new Thread(new 厨子B的工作流程(), "厨子B");
        厨子A.start();
        厨子B.start();
    }

    /**
     * 尝试获得锁
     */
    static class 厨子A的工作流程 implements Runnable {

        int food = 10;
        int finishFood = 0;

        @SneakyThrows
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            long last = System.currentTimeMillis();
            while (finishFood < food) {
                 System.out.println(name + "等待中");
                if (菜刀.tryLock()) {
                    System.out.println(name + "获得了菜刀");
                    if (案板.tryLock()) {
                        System.out.println(name + "获得了案板");
                        System.out.println(name + "开始切菜");
                        Thread.sleep(厨子A切菜时间);
                        finishFood ++;
                        System.out.println(name + "切菜完毕");
                        案板.unlock();
                    }
                    菜刀.unlock();
                    Thread.sleep(休息时间);
                }
            }
            System.out.println(name + "完成耗时:"+ (System.currentTimeMillis() - last));
        }
    }

    /**
     * 尝试获得锁
     */
    static class 厨子B的工作流程 implements Runnable {

        int food = 10;
        int finishFood = 0;

        @SneakyThrows
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            long last = System.currentTimeMillis();
            while (finishFood < food) {
                 System.out.println(name + "等待中");
                if (案板.tryLock()) {
                    System.out.println(name + "获得了案板");
                    if (菜刀.tryLock()) {
                        System.out.println(name + "获得了菜刀");
                        System.out.println(name + "开始切菜");
                        Thread.sleep(厨子B切菜时间);
                        finishFood ++;
                        System.out.println(name + "切菜完毕");
                        菜刀.unlock();
                    }
                    案板.unlock();
                    Thread.sleep(休息时间);
                }
            }
            System.out.println(name + "完成耗时:" + (System.currentTimeMillis() - last));
        }
    }

}
