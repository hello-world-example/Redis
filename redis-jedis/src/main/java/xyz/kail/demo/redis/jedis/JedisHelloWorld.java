package xyz.kail.demo.redis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import xyz.kail.demo.redis.jedis.util.JedisUtil;

/**
 * @author kail
 */
public class JedisHelloWorld {

    private static void string(Jedis jedis) {

        jedis.set("kail:foo", String.valueOf(System.currentTimeMillis()));

        System.out.println(jedis.get("kail:foo"));

        jedis.del("kail:foo");

        System.out.println(jedis.get("kail:foo"));
    }

    public static void main(String[] args) {

        try (final JedisPool jedisPool = JedisUtil.defaultJedisPool("172.16.2.219")) {

            // 使用完必须关闭，否则连接池用完， 后续获取资源时会 Block
            try (final Jedis jedis = jedisPool.getResource()) {
                // string 操作
                string(jedis);

            }

        }


    }

}
