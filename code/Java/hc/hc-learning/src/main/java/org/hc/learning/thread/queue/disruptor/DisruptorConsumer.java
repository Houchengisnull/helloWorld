package org.hc.learning.thread.queue.disruptor;


import com.lmax.disruptor.EventHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Data
public class DisruptorConsumer implements EventHandler<MessageEvent> {

    private String name;

    @Override
    public void onEvent(MessageEvent messageEvent, long l, boolean b) throws Exception {
        log.info("{} -> receive message:{}", name, messageEvent.getValue());
    }

}
