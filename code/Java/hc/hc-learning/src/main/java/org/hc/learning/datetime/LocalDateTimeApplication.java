package org.hc.learning.datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeApplication {

    public static void main(String[] args) throws InterruptedException {
        /**
         * ISO 8601标准格式 "yyyy-MM-dd'T'HH:mm.ss"
         */
        System.out.println("------------ 查看LocalDateTime对象是否随时间变化 ------------");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime last = LocalDateTime.now();
        String lastValue = formatter.format(last);
        System.out.println("last第一次格式化:" + lastValue);
        // 线程沉睡一秒
        Thread.sleep(1000);
        // 当通过now()获取对象实例后, 不随时间变化
        String lastValueSecond = formatter.format(last);
        System.out.println("last第二次格式化:" + lastValueSecond);
        LocalDateTime now = LocalDateTime.now();
        String nowValue = formatter.format(now);
        System.out.println("now 第一次格式化:" + nowValue);
        System.out.println("LocalDateTime对象是否随时间变化:" + nowValue.equals(lastValue));
    }

}
