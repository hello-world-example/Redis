package xyz.kail.demo.redis.jedis.ha;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

public class JedisSubMain {

    public static void main(String[] args) throws InterruptedException {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(200);
        jedisPoolConfig.setMinIdle(10);

        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setTestOnBorrow(true);
        // jedisPoolConfig.setTestOnBorrow(false);
        jedisPoolConfig.setTestOnReturn(false);

        // VIP
        // connect timed out 链接超时 connect timed out
        // 集群切单机 Thu Jun 27 15:18:48 CST 2019 ~~ Thu Jun 27 15:19:22 CST 2019
        // 单机切集群 ==Thu Jun 27 15:19:51 CST 2019 ~~ Thu Jun 27 15:20:07 CST 2019
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "172.16.2.77");
        // JedisPool jedisPool = new JedisPool(jedisPoolConfig, "172.16.2.77", 6379, 10_000);
        //
        //
        // HAProxy
        // 链接直接断开了
        // 配合 setTestOnBorrow(true); 影响不大
        // JedisPool jedisPool = new JedisPool(jedisPoolConfig, "172.16.2.86");


        Jedis jedis = jedisPool.getResource();

        JedisPubSub jedisPubSub = new JedisPubSub() {

            @Override
            public void onPMessage(String pattern, String channel, String message) {
                System.out.println(pattern + ":" + channel + ":" + message);
            }

            @Override
            public void onPUnsubscribe(String pattern, int subscribedChannels) {
                System.out.println("onUnsubscribe::" + pattern + ": subscribedChannels:" + subscribedChannels);
            }
        };

        // new Thread(() -> jedis.psubscribe(jedisPubSub, "hello", "sub.*")).start();

        // publish kail:subpub:hello
        jedis.psubscribe(jedisPubSub, "kail:subpub:hello");



    }

}
