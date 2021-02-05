package org.hc.learning.thread.题目.顺序执行;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 一道编程题目
 * 三条线程打印ABC, 各重复十次
 *
 * 实现思路:
 * 1. 让生产者保证有序, 消费者领取各自的任务
 */
@Slf4j
public class SortedExecuteTask {

    private static final int times = 10;
    private static BlockingQueue<String> tasks = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < times; i++) {
            tasks.put("A");
            tasks.put("B");
            tasks.put("C");
        }

        PrintTask printTask = new PrintTask();
        printTask.setTasks(tasks);

        Thread aThread = new Thread(printTask, "A");
        Thread bThread = new Thread(printTask, "B");
        Thread cThread = new Thread(printTask, "C");

        aThread.start();
        bThread.start();
        cThread.start();
    }

}
