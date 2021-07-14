package org.hc.learning.thread.semaphore;

import java.sql.Connection;

/**
 * Semaphore与Condition一样无法重入
 */
public class RecursionSemaphore {

    private static DBPoolSemaphore dbPool = new DBPoolSemaphore();

    public static void main(String[] args) throws InterruptedException {
        recursionTake();
    }

    public static void recursionTake() throws InterruptedException {
        Connection connection = dbPool.takeConnect();
        recursionTake();
        dbPool.returnConnect(connection);
    }

}
