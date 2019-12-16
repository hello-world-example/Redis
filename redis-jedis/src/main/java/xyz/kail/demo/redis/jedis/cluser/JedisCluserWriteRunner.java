package xyz.kail.demo.redis.jedis.cluser;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JedisCluserWriteRunner implements Runnable {

    private JedisPool jedisPool;

    private String key;

    public JedisCluserWriteRunner(JedisPool jedisPool, String key) {
        this.jedisPool = jedisPool;
        this.key = key;
    }

    @Override
    public void run() {
        for (;;) {
            Jedis jedis = jedisPool.getResource();
            try {
                jedis.set(key, "" + new Date());
            } catch (Exception ex) {
                System.out.println(Thread.currentThread().getName() + "-----" + new Date());
                ex.printStackTrace();
            } finally {
                if (null != jedis) {
                    jedis.close();
                }
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("record:" + new Date());

        }
    }

}
