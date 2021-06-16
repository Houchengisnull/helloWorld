package org.hc.learning.thread.jmm.question;

public class VolatileReferenceChange {

    private /*volatile*/ static RunHolder runHolder = new RunHolder();

    private static class RunHolder {
        public boolean isRun = true;
    }

    public static void main(String[] args) {
        int i = 0;

        new Thread(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runHolder.isRun = false;
            System.out.println("将isRun修改为false");
        }).start();

        while (runHolder.isRun) {
            i++;
            // System.out.println();
        }
        System.out.println("程序结束. i=" + i);
    }

}
