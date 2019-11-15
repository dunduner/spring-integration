package com.zn.kafka.lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class RedisWatchLock {

    private static final String redisHost = "127.0.0.1";
    private static final int port = 6379;
    private static JedisPoolConfig config;
    private static JedisPool pool;

    private static ExecutorService service;
    private static int count = 10;

    private static CountDownLatch latch;
    private static AtomicInteger Countor = new AtomicInteger(0);

    static {
        config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxWaitMillis(1000);
        config.setMaxTotal(30);
        pool = new JedisPool(config, redisHost, port);
        service = Executors.newFixedThreadPool(10);

        latch = new CountDownLatch(count);//10
    }

    public static void main(String[] args) {
        int count = 10;
        String ThreadNamePrefix = "thread-";
        Jedis cli = pool.getResource();
        System.out.println(cli.del("redis_inc_key"));// 先删除既定的key
        cli.set("redis_inc_key", String.valueOf(1));
        for (int i = 0; i < 1; i++) {
            Thread thread = new Thread(new TestThread(pool));
            thread.setName(ThreadNamePrefix + i);
            System.out.println(thread.getName() + "inited...");
            service.submit(thread);
        }
    }

    public static class TestThread implements Runnable {
        private String incKeyStr = "redis_inc_key";
        private static JedisPool pool;
        private Jedis cli;

        public TestThread(JedisPool pool) {
            cli = pool.getResource();
            this.pool = pool;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    actomicAdd();//生产环境中批量操作尽量使用redisPipeLine!!
                }
            } catch (Exception e) {
                pool.returnBrokenResource(cli);
            } finally {
                pool.returnResource(cli);
                latch.countDown();
            }

        }

        /**
         * 0 watch key
         * 1 multi
         * 2 set key value(queued)
         * 3 exec
         * <p>
         * return null：fail
         * reurn  "ok": succeed
         * <p>
         * watch每次都需要执行(注册)
         */
        public void actomicAdd() {
            cli.watch(incKeyStr);// 0.watch key
            boolean flag = true;
            while (flag) {
                String countStr = cli.get("redis_inc_key");
                int countInt = Integer.parseInt(countStr);
                int expect = countInt + 1;
                Transaction tx = cli.multi(); // 1.multi
                tx.set(incKeyStr, String.valueOf(expect));// 2.set key value
                // (queued)
                List<Object> list = tx.exec();// 3.exec
                if (list == null) {
                    System.out.println("fail");
                    continue;
                } else {
                    flag = false;
                    System.out.println("succeed");
                }
                System.out.println("my expect num is " + expect);
                System.out.println("seting....");
            }
            Countor.incrementAndGet();
        }

    }
}
