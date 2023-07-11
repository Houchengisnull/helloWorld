package org.hc.learning.thread.base.safeend;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;

/**
 * 阻塞状态下中断线程
 */
public class EndlessInterrupt {

    @SneakyThrows
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println(StrUtil.format("{} is running", Thread.currentThread().getName()));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
        Thread.sleep(5 * 1000);
        thread.interrupt();
    }
}
