package org.hc.learning.thread.container.blockingqueue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

class Cache implements Delayed {
	
	private long delayNanoTime; // 延迟纳秒
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Cache(long delayTime, String value) {
		TimeUnit unit = TimeUnit.SECONDS;
		delayNanoTime = System.nanoTime() + unit.toNanos(delayTime);
		this.value = value;
	}
	
	public long getDelayNanoTime() {
		return delayNanoTime;
	}

	@Override
	public int compareTo(Delayed o) {
		if ((this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS)) < 0) {
			return -1;
		}
		if ((this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS)) > 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(delayNanoTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}
	
}

public class TestDelayQueue {
	public static void main(String[] args) throws InterruptedException {
		DelayQueue<Cache> queue = new DelayQueue<>();
		queue.add(new Cache(1, "value 1"));
		queue.add(new Cache(2, "value 2"));
		queue.add(new Cache(3, "value 3"));
		queue.add(new Cache(4, "value 4"));
		queue.add(new Cache(5, "value 5"));
		System.out.println(System.currentTimeMillis());
		System.out.println(queue.take().getValue() + System.currentTimeMillis());
		System.out.println(queue.take().getValue() + System.currentTimeMillis());
		System.out.println(queue.take().getValue() + System.currentTimeMillis());
		System.out.println(queue.take().getValue() + System.currentTimeMillis());
		System.out.println(queue.take().getValue() + System.currentTimeMillis());
	}
}
