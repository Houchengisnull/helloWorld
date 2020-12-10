package org.hc.learning.test;

import org.junit.After;
import org.junit.Before;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestCase {

    private LocalDateTime last;
    private static Logger logger = LoggerFactory.getLogger(TestCase.class);
    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS");

    @Before
    public void giveLast() {
        last = LocalDateTime.now();
    }

    @After
    public void computeSpent() {
        LocalDateTime now = LocalDateTime.now();
        logger.debug("start time : {}", last.format(formatter));
        logger.debug("end time : {}", now.format(formatter));
        Duration duration = Duration.between(last, now);
        logger.debug("spent {} ms", duration.toMillis());
    }
}
