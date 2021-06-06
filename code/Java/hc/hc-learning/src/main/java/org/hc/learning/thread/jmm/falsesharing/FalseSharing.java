package org.hc.learning.thread.jmm.falsesharing;

/**
 * VolatileLong : duration = 44 153 643 700
 * VolatileLongAnno(未设置启动参数-XX:-RestrictContended) : duration = 41 105 135 400
 * VolatileLongPadding : duration = 6 432 228 400
 * VolatileLongAnno(设置启动参数-XX:-RestrictContended) : 6 700 221 701
 */
public class FalseSharing implements Runnable {

    public final static int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    public final static long ITERATIONS = 500L * 1000L * 1000L;
    private final int arrayIndex;

    private static VolatileLongAnno[] longs = new VolatileLongAnno[NUM_THREADS];
//    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];
//    private static VolatileLongPadding[] longs = new VolatileLongPadding[NUM_THREADS];

    static {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new VolatileLongAnno();
//            longs[i] = new VolatileLong();
//            longs[i] = new VolatileLongPadding();
        }
    }

    public FalseSharing(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception{
        final long start = System.nanoTime();
        runTest();
        System.out.println("duration = " + (System.nanoTime() - start));
    }

    private static void runTest() throws InterruptedException{
        /*创建和CPU数相同的线程*/
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < threads.length; i++){
            threads[i] = new Thread(new FalseSharing(i));
        }

        for (Thread t : threads){
            t.start();
        }

        /*等待所有线程执行完成*/
        for (Thread t : threads){
            t.join();
        }
    }

    /*访问数组*/
    public void run(){
        long i = ITERATIONS + 1;
        while (0 != --i){
            longs[arrayIndex].value = i;
        }
    }

    public final static class VolatileLong {
        public volatile long value = 0L;
    }

    // long padding避免false sharing
    // 按理说jdk7以后long padding应该被优化掉了，但是从测试结果看padding仍然起作用
    public final static class VolatileLongPadding {
        public long p1, p2, p3, p4, p5, p6, p7;
        public volatile long value = 0L;
        volatile long q0, q1, q2, q3, q4, q5, q6;
    }

    /**
     * jdk8新特性，Contended注解避免false sharing
     * Restricted on user classpath
     * Unlock: -XX:-RestrictContended
     */
    @sun.misc.Contended
    public final static class VolatileLongAnno {
        public volatile long value = 0L;
    }
}
