package com.zn.kafka.lock;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo01 {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        ExecutorService fixedthreadpool = Executors.newFixedThreadPool(3);
        for(int i=0; i<5; i++) {//5个活需要干，但是只有3个人做
            fixedthreadpool.execute(myThread);
        }
        fixedthreadpool.shutdown();
    }
}

class MyThread implements Runnable{
    @Override
    public void run() {
            System.out.println(Thread.currentThread().getName());
    }
    //线程1干了2次活   线程3也干了2次  线程2 干了一次  活干完了

    //pool-1-thread-1
    //pool-1-thread-3
    //pool-1-thread-2
    //pool-1-thread-3
    //pool-1-thread-1

}