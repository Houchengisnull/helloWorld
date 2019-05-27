package org.hc.learning.thread.container.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import org.junit.Test;

public class TestArrayBlockingQueue {
	@Test
	public void put() throws InterruptedException {
		// 有界阻塞队列
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
		queue.put("a1");
		queue.put("a2");
		queue.put("a3");
		System.out.println(queue.size());
		System.out.println("放入a4元素");
		queue.put("a4"); // 线程阻塞
		System.out.println("hello world");
	}
	
	@Test
	public void take() throws InterruptedException {
		// 有界阻塞队列
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
		System.out.println(queue.size());
		System.out.println(queue.take());
		System.out.println("hello world");
	}
}
