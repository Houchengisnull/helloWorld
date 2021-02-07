package org.hc.learning.thread.题目.顺序执行;

import lombok.extern.slf4j.Slf4j;

/**
 * 一道编程题目
 * 三条线程打印ABC, 各重复十次
 *
 * 实现思路: 等待通知模型
 */
@Slf4j
public class SortedExecuteTaskByNotifyWait {

    public static void main(String[] args) {
/*
        Thread aThread = new Thread(new StatePrintTask(0), "A");
        Thread bThread = new Thread(new StatePrintTask(1), "B");
        Thread cThread = new Thread(new StatePrintTask(2), "C");
*/
        Integer state = new Integer(0);
        Thread aThread = new Thread(new StatePrintTask(0), "A");
        Thread bThread = new Thread(new StatePrintTask(1), "B");
        Thread cThread = new Thread(new StatePrintTask(2), "C");

        /*Thread cThread = new Thread(new StatePrintTask(2), "C");*/
        aThread.start();
        bThread.start();
        cThread.start();
        /*cThread.start();*/
    }

}
