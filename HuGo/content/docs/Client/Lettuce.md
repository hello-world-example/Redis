# Lettuce

> 官网： https://lettuce.io/
>
> 官方文档： https://lettuce.io/docs/getting-started.html



Spring Boot 2 的 Redis 客户端 从 `Jedis` 换成了 `Lettuce`，所以注意到了它。

Lettuce 是一个可伸缩的线程安全的 Redis 客户端，支持同步、异步和响应式模式。

**多个线程可以共享一个连接实例，而不必担心多线程并发问题**。

它基于优秀 Netty NIO 框架构建，支持 Redis 的高级功能，如 Sentinel，集群，流水线，自动重新连接和 Redis 数据模型。



## 示例代码

```bash
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
```

