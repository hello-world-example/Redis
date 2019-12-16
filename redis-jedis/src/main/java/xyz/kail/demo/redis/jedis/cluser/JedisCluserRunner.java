package xyz.kail.demo.redis.jedis.cluser;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisCluserRunner {

    public static void main(String[] args) throws InterruptedException, ConfigurationException {

        Configurations configs = new Configurations();
        PropertiesConfiguration properties =
                configs.properties(JedisCluserRunner.class.getResource("/config.properties"));

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


        new Thread(new JedisCluserReadRunner(jedisPool, redisKey1), "Thread-Read" + redisKey1).start();
        new Thread(new JedisCluserReadRunner(jedisPool, redisKey2), "Thread-Read" + redisKey2).start();
        new Thread(new JedisCluserReadRunner(jedisPool, redisKey3), "Thread-Read" + redisKey3).start();

        new Thread(new JedisCluserWriteRunner(jedisPool, redisKey1), "Thread-Write" + redisKey1).start();
        new Thread(new JedisCluserWriteRunner(jedisPool, redisKey2), "Thread-Write" + redisKey2).start();
        new Thread(new JedisCluserWriteRunner(jedisPool, redisKey3), "Thread-Write" + redisKey3).start();
    }


}
