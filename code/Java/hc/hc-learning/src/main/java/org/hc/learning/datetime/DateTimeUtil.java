package org.hc.learning.datetime;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 使用DateTimeFormatter时,
 * 在Date\Long\Instant\LocalDateTime/ZonedDateTime中相互转换时代码冗长
 * 不利于阅读，为了提高可读性，写个工具类来简化转换
 */
public class DateTimeUtil {

    /*@Test
    public void test(){
        String date = "2021-11-04 18:00:00";
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = toLocalDateTime(date, pattern);
        ZonedDateTime zonedDateTime = toZonedDateTime(date, pattern, ZoneId.systemDefault());
        System.out.println(toString(zonedDateTime, pattern, ZoneId.systemDefault()));
        System.out.println(toString(zonedDateTime.toLocalDateTime(), pattern));
    }*/

    /**
     * for example, toLocalDateTime("2021-11-04 18:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
     * String >> LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(String text, DateTimeFormatter pattern) {
        return LocalDateTime.parse(text, pattern);
    }

    /**
     *
     * String >> ZonedDateTime
     */
    public static ZonedDateTime toZonedDateTime(String text, DateTimeFormatter pattern, ZoneId zone) {
        return ZonedDateTime.parse(text, pattern.withZone(zone));
    }

    /**
     * LocalDateTime > ZonedDateTime
     */
    public static ZonedDateTime toZonedDateTime(LocalDateTime localDateTime, ZoneId zone) {
        return localDateTime.atZone(zone);
    }

    /**
     * Instant >> ZonedDateTime
     */
    public static ZonedDateTime toZonedDateTime(Instant instant, ZoneId zone) {
        return instant.atZone(zone);
    }

    /**
     * ZonedDateTime >> String
     */
    public static String toString(ZonedDateTime zonedDateTime, DateTimeFormatter pattern, ZoneId zone) {
        return pattern.withZone(zone).format(zonedDateTime);
    }

    public static String toString(LocalDateTime localDateTime, DateTimeFormatter pattern) {
        return pattern.format(localDateTime);
    }

    public static Instant toInstant(LocalDateTime localDateTime, ZoneId zone) {
        return localDateTime.atZone(zone).toInstant();
    }

    public static Instant toInstant(String text, DateTimeFormatter pattern, ZoneId zone) {
         return ZonedDateTime.parse(text, pattern.withZone(zone)).toInstant();
    }

}
