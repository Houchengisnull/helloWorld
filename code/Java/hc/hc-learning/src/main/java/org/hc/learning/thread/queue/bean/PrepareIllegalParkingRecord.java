package org.hc.learning.thread.queue.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 预违停记录
 */
@AllArgsConstructor
@Data
public class PrepareIllegalParkingRecord implements Delayed {

    private String plateNum;
    private Date capTime;
    private static final long DELAY = 5000;

    @Override
    public long getDelay(TimeUnit unit) {
        return capTime.getTime() + DELAY - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
