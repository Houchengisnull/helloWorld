package org.hc.learning.thread.lock.readwrite;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UseReadLockSimple {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    ReadARunnable readARunnable = new ReadARunnable();
    ReadBRunnable readBRunnable = new ReadBRunnable();
    WriteRunnable writeRunnable = new WriteRunnable();

    public static void main(String[] args) {
        UseReadLockSimple useReadLockSimple = new UseReadLockSimple();
        ReadARunnable readARun = useReadLockSimple.readARunnable;
        ReadBRunnable readBRun = useReadLockSimple.readBRunnable;
//        WriteRunnable writeRun = useReadLockSimple.writeRunnable;

        /*Thread thread = new Thread(writeRun);
        thread.start();*/
        /*try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        new Thread(readARun).start();
        new Thread(readBRun).start();
    }

    class WriteRunnable implements Runnable {

        @Override
        public void run() {
            writeLock.lock();
            System.out.println("Write Lock " +  Thread.currentThread().getId());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
        }
    }

    class ReadARunnable implements Runnable {

        @Override
        public void run() {
            readLock.lock();
            System.out.println("Read A Lock " +  Thread.currentThread().getId());
            // 无释放锁操作
        }
    }

    class ReadBRunnable implements Runnable {

        @Override
        public void run() {
            readLock.lock();
            System.out.println("Read B Lock " +  Thread.currentThread().getId());
            // 无释放锁操作
        }
    }
}
