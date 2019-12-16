package xyz.kail.demo.redis.jedis.cluser;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JedisCluserMain {

    public static void main(String[] args) throws InterruptedException, ConfigurationException {

        Configurations configs = new Configurations();
        PropertiesConfiguration properties =
                configs.properties(JedisCluserMain.class.getResource("/config.properties"));

        String redisIp = properties.getString("redis.ip");
        String redisKey1 = properties.getString("redis.key1");
        String redisKey2 = properties.getString("redis.key2");
        String redisKey3 = properties.getString("redis.key3");

        System.out.println(properties.getString("redis.ip"));
        System.out.println(properties.getString("redis.key1"));
        System.out.println(properties.getString("redis.key2"));
        System.out.println(properties.getString("redis.key3"));


        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(200);
        jedisPoolConfig.setMinIdle(10);

        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setTestOnBorrow(false);
        jedisPoolConfig.setTestOnReturn(false);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisIp);

        for (;;) {
            Jedis jedis = jedisPool.getResource();
            print(jedis, redisKey1);
            // 2
            jedis = jedisPool.getResource();
            print(jedis, redisKey2);
            // 3
            jedis = jedisPool.getResource();
            print(jedis, redisKey3);

            System.out.println("==" + new Date());
            TimeUnit.MILLISECONDS.sleep(1000);

        }
    }

    private static void print(Jedis jedis, String key) throws InterruptedException {
        try {
            jedis.set(key, "" + new Date());
            jedis.get(key);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("-----" + new Date());
            // TimeUnit.SECONDS.sleep(1);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

}
