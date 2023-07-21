package org.hc.web.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadPoolUtil {

    private static final Executor single = Executors.newSingleThreadExecutor();

    /**
     * 由单线程的线程池执行
     * @param runnable
     */
    public static void executeBySingle(Runnable runnable) {
        single.execute(runnable);
    }
}
