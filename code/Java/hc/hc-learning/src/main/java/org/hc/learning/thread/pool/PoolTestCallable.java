package org.hc.learning.thread.pool;

import java.util.concurrent.Callable;

public class PoolTestCallable implements Callable<String> {

    private int seqNo;

    public PoolTestCallable(){}
    public PoolTestCallable(int seqNo) {
        this.seqNo = seqNo;
    }

    @Override
    public String call() throws Exception {
        return "编号" + seqNo;
    }
}
