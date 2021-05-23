package org.hc.learning.jvm.allocation;

/**
 * 对象优先分配在Eden
 * -Xms 堆最小容量
 * -Xmx 堆最大容量
 * -Xmn 新生代大小
 * -XX:+PrintGCDetails 打印GC信息
 */
public class EdenAllocation {

    /**
     * int 占据4 byte
     * 1024 byte = 1 KB
     * 1024 KB   = 1 MB
     */
    private final static int _1MB = 1024 * 1024;
    public static void main(String[] args) {
        byte[] b1,b2,b3,b4;
        b1 = new byte[_1MB];
        b2 = new byte[_1MB];
        b3 = new byte[_1MB];
        b4 = new byte[_1MB];
    }

}
