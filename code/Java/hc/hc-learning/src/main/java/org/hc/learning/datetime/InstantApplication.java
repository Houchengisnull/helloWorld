package org.hc.learning.datetime;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class InstantApplication {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
    public static void main(String[] args) {
        System.out.println("------------ Instant获取时间戳");
        System.out.println("Instant.now().toEpochMilli()" + Instant.now().toEpochMilli());
        System.out.println("System.currentTimeMillis:" + System.currentTimeMillis());

        System.out.println("------------ 创建ZonedDateTime对象");
        Instant now = Instant.now();
        // 设置系统默认时区
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        System.out.println(zonedDateTime);
        System.out.println(zonedDateTime.format(formatter));

    }

}
