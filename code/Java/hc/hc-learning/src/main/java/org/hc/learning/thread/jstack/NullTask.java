package org.hc.learning.thread.jstack;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class NullTask implements Runnable{

    @Override
    public void run() {
        while (true){

        }
    }

    public static void main(String[] args) {
        /*Thread thread = new Thread(new NormalTask());
        thread.start();*/
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(new NullTask());
        service.submit(new NullTask());
    }
}
