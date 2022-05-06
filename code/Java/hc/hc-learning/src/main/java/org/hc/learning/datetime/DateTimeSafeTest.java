package org.hc.learning.datetime;

import cn.hutool.core.date.DateUtil;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Date线程安全性测试
 * 结论: Date不是线程安全的、且是可变的
 * com.hutool.core.date.DateUtil.offsetMinute将返回新的Date对象，
 * 才使得以下代码正确运行
 */
public class DateTimeSafeTest {

    private static Date startTime;
    /*private static Date end;*/
    private static Lock lock = new ReentrantLock();

    public DateTimeSafeTest() {

    }

    public static void main(String[] args) throws InterruptedException {
        startTime = new Date();
        Date start = null;
        Date end = null;
        System.out.println("startTime:" + startTime);

        lock.lock();
        try {
            start = (Date) startTime.clone();
            startTime = DateUtil.offsetMinute(startTime, 1);
            end = startTime;
            System.out.println("start:" + start);
            System.out.println("end:" + end);
            System.out.println("startTime:" + startTime);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                startTime = DateUtil.offsetMinute(startTime, 1);
                System.out.println("线程修改startTime");
            }
        }).start();

        Thread.sleep(1000l);

        System.out.println("start:" + start);
        System.out.println("end:" + end);
        System.out.println("startTime:" + startTime);
    }
}
