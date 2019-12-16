package xyz.kail.demo.redis.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;

public class Main {

    public static void main(String[] args) {

RedisClient redisClient = RedisClient.create("redis://password@localhost:6379/0");

try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {

    // ❤❤❤ 估计是 Spring Boot 2 基于 Spring 5，与新增的 Webflux 比较搭  ❤❤❤
    final RedisReactiveCommands<String, String> reactive = connection.reactive();

    // 异步请求
    final RedisAsyncCommands<String, String> async = connection.async();

    // 同步请求
    RedisCommands<String, String> syncCommands = connection.sync();

    syncCommands.set("key", "Hello, Redis!");

}


redisClient.shutdown();
    }

}
