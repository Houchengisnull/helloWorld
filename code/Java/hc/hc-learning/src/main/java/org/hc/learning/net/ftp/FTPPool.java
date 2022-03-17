package org.hc.learning.net.ftp;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * ****难点 FTP连接池中连接数量与线程池的线程数量不同
 */
public class FTPPool {

    // 应该用CompletionService来包装一下
    private ExecutorService service;

    public FTPPool(ExecutorService service) {
        this.service = service;
    }

    public void extract(String directory) {
        Future<String> queryResult = service.submit(new QueryTask(directory));
    }

    public static class QueryTask implements Callable<String> {

        private String directory;

        public QueryTask(String directory) {
            this.directory = directory;
        }

        @Override
        public String call() throws Exception {
            return null;
        }
    }

}
