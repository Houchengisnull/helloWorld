package org.hc.learning.datetime;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class LocalDateTimeApplication {

    public static void main(String[] args) throws InterruptedException {
        /**
         * ISO 8601标准格式 "yyyy-MM-dd'T'HH:mm.ss"
         */
        System.out.println("------------ formatter ------------");
        DateTimeFormatter isoDate = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime.format(isoDate));
        Thread.sleep(1000);
        // 当通过now()获取对象实例后, 不随时间变化
        System.out.println(dateTime.format(isoDate));
        System.out.println(LocalDateTime.now().format(isoDate));
        System.out.println("------------ adjustInto ------------");
        /*
        java.time.LocalDateTime.adjustInto(Temporal temporal)方法将指定的时态对象调整为与此对象具有相同的日期和时间
         */
        LocalDateTime now = LocalDateTime.now();
        Thread.sleep(1000);
        System.out.println(now.toLocalTime());
        OffsetDateTime temp = OffsetDateTime.now();
        System.out.println("before adjust to temp:" + temp.toLocalTime());
        Temporal temporal = now.adjustInto(temp);
        System.out.println("after adjust to temp:" + temp);
        System.out.println("after adjust to templ:" + temporal);

    }

}
