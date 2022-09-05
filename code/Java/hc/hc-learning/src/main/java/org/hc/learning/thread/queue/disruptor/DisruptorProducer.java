package org.hc.learning.thread.queue.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class DisruptorProducer {

    private Disruptor disruptor;

    public void send(String data) {
        RingBuffer<MessageEvent> ringBuffer = this.disruptor.getRingBuffer();
        long next = ringBuffer.next();
        try {
            MessageEvent msg = ringBuffer.get(next);
            msg.setValue(data);
        } finally {
            log.info("send {}", data);
            ringBuffer.publish(next);
        }
    }

    public void send(List<String> l){
        for (String s : l) {
            this.send(s);
        }
    }
}
