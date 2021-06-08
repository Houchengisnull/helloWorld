package org.hc.learning.thread.jmm.question;

import lombok.SneakyThrows;

public class VolatileChange {

    private static /*volatile*/ boolean isRun = true;

    public static void main(String[] args) {
        int i = 0;
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(2000);
                isRun = false;
                System.out.println("将isRun修改为false");
            }
        }).start();
        while (isRun) {
            i++;
            // System.out.println();
        }
        System.out.println("程序结束. i=" + i);
    }

}
