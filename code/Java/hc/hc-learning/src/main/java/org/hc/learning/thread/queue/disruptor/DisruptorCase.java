package org.hc.learning.thread.queue.disruptor;

import com.lmax.disruptor.dsl.Disruptor;
import org.hc.learning.test.TestCase;
import java.util.Arrays;
import java.util.concurrent.Executors;

public class DisruptorCase extends TestCase {

    public static void main(String[] args) {
        Disruptor<MessageEvent> d = new Disruptor<>(MessageEvent::new, 1024, Executors.defaultThreadFactory());

        DisruptorConsumer one = new DisruptorConsumer("one");
        DisruptorConsumer two = new DisruptorConsumer("two");
        DisruptorConsumer three = new DisruptorConsumer("three");

        d.handleEventsWith(one, two, three);
        d.start();

        DisruptorProducer p = new DisruptorProducer(d);
        p.send(Arrays.asList("h", "e", "l", "l","o"));
    }

}
