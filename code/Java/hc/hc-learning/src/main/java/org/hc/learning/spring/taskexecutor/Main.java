package org.hc.learning.spring.taskexecutor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TaskExecutorConfig.class);
		AsyncTaskService bean = context.getBean(AsyncTaskService.class);
		for (int i = 0 ; i < 10 ; i++ ) {
			bean.executeAsyncTask(i);
			bean.executeAsyncTaskPlus(i);
		}
		context.close();
	}
}
