package org.hc.learning.datetime;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class InstantApplication {

    public static void main(String[] args) {
        System.out.println("------------ Instant获取当前时间");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
        System.out.println(Instant.now());
        Instant now = Instant.now();

        System.out.println("------------ 与ZonedDateTime比较");
        // 设置系统默认时区
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        System.out.println(zonedDateTime);
        String format = zonedDateTime.format(formatter);
        System.out.println(format);
        System.out.println("------------ Instant获取时间戳");
        // 通过Instant获取时间戳 <==> System.currentTimeMillis()
        long timestamp = Instant.now().toEpochMilli();
        System.out.println(timestamp);
        System.out.println(System.currentTimeMillis());
    }

}
