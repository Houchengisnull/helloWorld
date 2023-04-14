package org.hc.learning.thread.queue;

import cn.hutool.core.date.DateUtil;
import lombok.SneakyThrows;
import org.hc.learning.thread.queue.bean.PrepareIllegalParkingRecord;
import java.util.Date;
import java.util.concurrent.DelayQueue;

public class DelayQueueApplication {

    public static void main(String[] args) {
        DelayQueue<PrepareIllegalParkingRecord> queue = new DelayQueue<>();
        queue.add(new PrepareIllegalParkingRecord("粤A1120", new Date()));
        queue.add(new PrepareIllegalParkingRecord("粤K1568", new Date()));
        queue.add(new PrepareIllegalParkingRecord("粤C0256", new Date()));
        System.out.println(DateUtil.now());
        Thread consumer = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    PrepareIllegalParkingRecord record = queue.take();
                    System.out.println(DateUtil.now() + " 车牌号码:" + record.getPlateNum() + " 抓拍时间:" + DateUtil.date(record.getCapTime()));
                }
            }
        });

        consumer.start();
    }

}
