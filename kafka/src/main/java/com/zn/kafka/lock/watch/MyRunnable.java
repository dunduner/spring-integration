package com.zn.kafka.lock.watch;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;

public class MyRunnable implements Runnable {
    String watchkeys = "watchkeys";// 监视keys
    Jedis jedis = new Jedis("127.0.0.1", 6379);

    public MyRunnable() {
    }

    @Override
    public void run() {
        try {
            jedis.watch(watchkeys);// watchkeys

            String countStr = jedis.get(watchkeys);
            int countInt = Integer.valueOf(countStr);

            int expect = countInt - 1;
            String userifo = UUID.randomUUID().toString();
            if (countInt >0) {

                Transaction tx = jedis.multi();// 开启事务

                tx.set(watchkeys,String.valueOf(expect));

                List<Object> list = tx.exec();// 提交事务，如果此时watchkeys被改动了，则返回null
                if (list != null) {
                    System.out.println("用户：" + userifo + "抢购成功");
                    /* 抢购成功业务逻辑 */
                    jedis.sadd("setsucc", userifo);
                } else {
                    System.out.println("用户：" + userifo + "抢购失败");
                    /* 抢购失败业务逻辑 */
                    jedis.sadd("setfail", userifo);
                }

            } else {
                System.out.println("用户：" + userifo + "抢购失败");
                jedis.sadd("setfail", userifo);
                // Thread.sleep(500);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }

    }
}
