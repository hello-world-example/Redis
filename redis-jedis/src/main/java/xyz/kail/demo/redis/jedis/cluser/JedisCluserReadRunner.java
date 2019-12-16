package xyz.kail.demo.redis.jedis.cluser;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JedisCluserReadRunner implements Runnable {

    private JedisPool jedisPool;

    private String key;

    public JedisCluserReadRunner(JedisPool jedisPool, String key) {
        this.jedisPool = jedisPool;
        this.key = key;
    }

    @Override
    public void run() {
        for (;;) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();

                jedis.get(key);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(Thread.currentThread().getName() + "-----" + new Date());
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
