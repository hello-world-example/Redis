package xyz.kail.demo.redis.jedis.util;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class JedisUtil {

    public static JedisPool defaultJedisPool(String host) {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 0
        jedisPoolConfig.setMinIdle(JedisPoolConfig.DEFAULT_MIN_IDLE);
        // 8
        jedisPoolConfig.setMaxIdle(JedisPoolConfig.DEFAULT_MAX_IDLE);
        // 8
        jedisPoolConfig.setMaxTotal(JedisPoolConfig.DEFAULT_MAX_TOTAL);
        // true 当资源池用尽后，调用者是否要等待。只有当值为true时，下面的 MaxWaitMillis 才会生效
        jedisPoolConfig.setBlockWhenExhausted(JedisPoolConfig.DEFAULT_BLOCK_WHEN_EXHAUSTED);
        // -1 表示永不超时
        jedisPoolConfig.setMaxWaitMillis(JedisPoolConfig.DEFAULT_MAX_WAIT_MILLIS);


        // 1000L * 60L * 30L
        jedisPoolConfig.setMinEvictableIdleTimeMillis(JedisPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        // 3
        jedisPoolConfig.setNumTestsPerEvictionRun(JedisPoolConfig.DEFAULT_NUM_TESTS_PER_EVICTION_RUN);
        // -1
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(JedisPoolConfig.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS);

        // 10_000
        jedisPoolConfig.setEvictorShutdownTimeoutMillis(JedisPoolConfig.DEFAULT_EVICTOR_SHUTDOWN_TIMEOUT_MILLIS);
        // false
        jedisPoolConfig.setFairness(JedisPoolConfig.DEFAULT_FAIRNESS);

        // false
        jedisPoolConfig.setTestOnCreate(JedisPoolConfig.DEFAULT_TEST_ON_CREATE);
        // false
        jedisPoolConfig.setTestOnBorrow(JedisPoolConfig.DEFAULT_TEST_ON_BORROW);
        // false
        jedisPoolConfig.setTestWhileIdle(JedisPoolConfig.DEFAULT_TEST_WHILE_IDLE);
        // false
        jedisPoolConfig.setTestOnReturn(JedisPoolConfig.DEFAULT_TEST_ON_RETURN);

        return new JedisPool(jedisPoolConfig, host, Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT);
    }

}
