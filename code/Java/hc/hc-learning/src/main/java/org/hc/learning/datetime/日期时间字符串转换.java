package org.hc.learning.datetime;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class 日期时间字符串转换 {

    DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    public void localDateTime2String(){
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(pattern);
        log.debug("dateTime >> string -------- dateTime.format() >> {}", format);
    }

    /**
     * DateTime >> 时间戳
     * 时间戳   >> long
     */
    @Test
    public void localDateTime2Instant(){
        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        log.debug("dateTime >> instant -------- dateTime.toInstant() >> {}", instant);
    }

    @Test
    public void instant2String() {
        Instant now = Instant.now();
        String nowString = now.toString();
        log.debug("instant >> string -------- instant.toString() >> {}", nowString);
    }

    @Test
    public void instant2Long() {
        Instant now = Instant.now();
        long l = now.toEpochMilli();
        log.debug("instant >> long -------- instant.toEpochMilli() >> {}", l);
    }

    @Test
    public void instant2DateTime() {
        Instant now = Instant.now();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(now, ZoneId.systemDefault());
        log.debug("instant >> dateTime -------- LocaleDateTime.ofInstant() >> {}", localDateTime);
    }

    @Test
    public void string2DateTime() {
        String string = "2021-01-14 03:26:00";
        LocalDateTime datetime = LocalDateTime.parse(string, pattern);
        log.debug("string >> LocalDate -------- LocalDateTime.parse(String, DateTimeFormatter) >> {}", datetime);
        log.debug("string >> LocalDate -------- LocalDateTime.parse(String, DateTimeFormatter) [北京时间戳] >> {}", datetime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    @Test
    public void string2Instant() {
        String string = "2021-01-14 03:26:00";
        Instant instant = LocalDateTime.parse(string, pattern).toInstant(ZoneOffset.UTC);
        log.debug("string >> Instant -------- LocalDateTime.parse().toInstant() >> {}", instant);
        log.debug("string >> Instant -------- LocalDateTime.parse().toInstant().toEpochMilli() [UTC时间戳]>> {}", instant.toEpochMilli());
    }

    @Test
    public void long2Instant(){
        long l = System.currentTimeMillis();
        Instant instant = Instant.ofEpochMilli(l);
        log.debug("long >> Instant -------- Instant.ofEpochMilli({}) >> {} ", l , instant.getEpochSecond());
    }
}
