package org.hc.learning.net.ftp;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConcurrentFTPApplication {

    private static final int CORE = 1;
    private static final int MAX = 10;
    private static final long ALIVE = 1000l;
    private static final int QUEUE = 10;

    public static void main(String[] args) {
        ThreadPoolExecutor service = new ThreadPoolExecutor(CORE, MAX, ALIVE
                , TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(QUEUE));

        // init
        FTPPool pool = new FTPPool(service);
        // extract
        pool.extract("/");

    }

}
