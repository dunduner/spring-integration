package com.zn.kafka.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyExecutor extends Thread {
    private int index;

    public MyExecutor(int i) {
        this.index = i;
    }

    public void run() {
        try {
            System.out.println("[" + this.index + "] start....");
            Thread.sleep((int) (Math.random() * 10000));
            System.out.println("[" + this.index + "] end.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        ExecutorService service = Executors.newFixedThreadPool(2);
//        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {//模拟10个人 同时需要做这件事，但是只有2个线程干活
            service.execute(new MyExecutor(i));
            // service.submit(new MyExecutor(i));
        }
        System.out.println("submit finish");
        service.shutdown();
    }
}