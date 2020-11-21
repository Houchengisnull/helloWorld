package org.hc.learning.datetime;

import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalDateTimeInConcurrency {

    static LocalDateTime lastAccess = LocalDateTime.now();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss'Z'");
    static final Integer threadNum = 5;

    public static void main(String[] args ) {
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        for (Integer i = 0; i < threadNum; i++) {
            /*executorService.execute(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    while (true) {
                        *//*synchronized (lastAccess) {*//* // 由于lastAccess对象的引用不断在改变，所以依然会出现线程安全问题
                        synchronized (LocalDateTimeInConcurrency.class) {
                            String format = lastAccess.format(formatter);
                            System.out.println(Thread.currentThread().getName() + " get last Access:" + format);
                            lastAccess = LocalDateTime.now();
                            Thread.sleep(1000);
                        }
                    }
                }
            });*/
        }

    }

}
