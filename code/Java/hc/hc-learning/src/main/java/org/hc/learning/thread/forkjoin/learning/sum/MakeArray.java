package org.hc.learning.thread.forkjoin.learning.sum;

import java.util.Random;

/**
 * 生成随机数队列数组
 */
public class MakeArray {
    // 数组长度
    public static final int ARRAY_LENGTH = 100000;
    public static final int THRESHOLD = 47;

    public static Integer[] makeArray() {
        Random random = new Random(); // 随机数产生器
        Integer[] result = new Integer[ARRAY_LENGTH];
        for (int i = 0; i < ARRAY_LENGTH ; i++) {
            result[i] = random.nextInt(ARRAY_LENGTH * 3);
        }
        return result;
    }
}
