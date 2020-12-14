package org.hc.learning.thread.pool;

import lombok.SneakyThrows;
import org.hc.learning.test.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMyThreadPool extends TestCase {

	private static final Logger logger = LoggerFactory.getLogger(TestMyThreadPool.class);
	private static final int taskNum = 32;
	private MyThreadPool pool = new MyThreadPool();

	@Test
	public void 提交任务() throws InterruptedException {
		for (int i = 0; i < taskNum; i++) {
			pool.execute(new PoolTestTask(i));
		}
		Thread.sleep(1000);
	}

	static class PoolTestTask implements Runnable {

		private int taskSeq;

		PoolTestTask(int taskSeq) {
			this.taskSeq = taskSeq;
		}

		@SneakyThrows
		@Override
		public void run() {
			Thread.sleep(50);
			logger.debug("Task {} has been finished", taskSeq);
		}
	}

}
