package org.hc.learning.thread.pool;

import org.hc.learning.test.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class TestMyThreadPool extends TestCase {

	private static final Logger logger = LoggerFactory.getLogger(TestMyThreadPool.class);
	private static final int taskNum = 32;
	private MyThreadPool pool = new MyThreadPool();

	private static final int coreSize = 10;
	private static final int maxSize = 20;
	private static final BlockingQueue queue = new ArrayBlockingQueue(taskNum);
	private ThreadPoolExtend extPool = new ThreadPoolExtend(coreSize, maxSize, 100, TimeUnit.SECONDS, queue);

	@Test
	public void 提交任务() throws InterruptedException {
		for (int i = 0; i < taskNum; i++) {
			pool.execute(new PoolTestTask(i));
		}
		Thread.sleep(1000);
	}

	@Test
	public void 执行扩展线程池() throws InterruptedException {
		/*for (int i = 0; i < taskNum; i++) {
			extPool.execute(new PoolTestTask(i));
		}
		Thread.sleep(1000);
		extPool.shutdownNow();
		Thread.sleep(1000);*/
		List<PoolTestCallable> tasks = new ArrayList<>();
		for (int i = 0; i < taskNum; i++) {
			tasks.add(new PoolTestCallable(i));
		}
		//
		extPool.invokeAll(tasks);
		extPool.shutdownNow();
	}

}
