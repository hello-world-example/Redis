# Jedis 链接池

Jedis 使用阻塞的 I/O，且其方法调用都是同步的，程序需要等到 Sockets 处理完 I/O 才能执行，不支持异步。

**实例不是线程安全的，所以需要通过连接池来使用 Jedis**。



## 连接池配置示例

> 官方文档 [Getting-started](https://github.com/xetorthio/jedis/wiki/Getting-started)

```java
JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
// 0
jedisPoolConfig.setMinIdle(JedisPoolConfig.DEFAULT_MIN_IDLE);
// 8
jedisPoolConfig.setMaxIdle(JedisPoolConfig.DEFAULT_MAX_IDLE);
// 8
jedisPoolConfig.setMaxTotal(JedisPoolConfig.DEFAULT_MAX_TOTAL);
// -1
jedisPoolConfig.setMaxWaitMillis(JedisPoolConfig.DEFAULT_MAX_WAIT_MILLIS);


// 1000L * 60L * 30L
jedisPoolConfig.setMinEvictableIdleTimeMillis(JedisPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
// 3
jedisPoolConfig.setNumTestsPerEvictionRun(JedisPoolConfig.DEFAULT_NUM_TESTS_PER_EVICTION_RUN);
// -1
jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(JedisPoolConfig.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
// true
jedisPoolConfig.setBlockWhenExhausted(JedisPoolConfig.DEFAULT_BLOCK_WHEN_EXHAUSTED);
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

new JedisPool(jedisPoolConfig, host, Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT);
```



使用 JedisPoolConfig 初始化配置会进行默认社会组

```java
public class JedisPoolConfig extends GenericObjectPoolConfig {
  
    public JedisPoolConfig() {
        // 开启空闲检查
        this.setTestWhileIdle(true);
        // Pool 中资源的最小空闲时间（单位为毫秒），达到此值后空闲资源将被移除
        this.setMinEvictableIdleTimeMillis(60_000L);
        // 空闲资源的检测周期（单位为毫秒）
        this.setTimeBetweenEvictionRunsMillis(30_000L);
        // 做空闲资源检测时，每次检测资源的个数。如果设置为 -1，就是对所有连接做空闲监测
        this.setNumTestsPerEvictionRun(-1);
    }
}
```



## JedisPoolConfig 配置说明

| 参数                              |   默认值    |     建议值      | 说明                                                         |
| :-------------------------------- | :---------: | :-------------: | ------------------------------------------------------------ |
| **minIdle**                       |      0      |     ~~10~~      | 【需根据业务场景】空闲链接的最小数量，负值代表没有限制，保证 Pool 内最少有指定数量的链接，即使是空闲的，使得下次可以直接使用，而无需重新创建 |
| **maxIdle**                       |      8      |     ~~50~~      | 【需根据业务场景】空闲链接的最大数量，负值代表没有限制，     |
| **maxTotal**                      |      8      |     ~~100~~     | 【需根据业务场景】<br />Pool 大小，该配置需要结合当前系统的并发量<br />如果当前业务峰值并发量在 100左右，配置成 100~150 即可<br />如果配置过小，比如默认值 8，当并发量到 10 的时候，可能前8个链接正在内使用还未归还，后2个请求无法从 Pool 中获取链接，会造成 阻塞 |
| blockWhenExhausted                |    true     |      true       | 当资源池用尽后，调用者是否要等待。<br />只有当值为true时，下面的 `maxWaitMillis` 才会生效 |
| **maxWaitMillis**                 |     -1      |   `1000`(1s)    | 当资源池连接用尽后，调用者的最大等待时间                     |
|                                   |             |                 |                                                              |
| ~~maxActive~~                     |      ~      |        ~        | ~~没有这个参数~~                                             |
|                                   |             |                 |                                                              |
| testOnCreate                      |    false    |     `false`     | 在创建链接时候 `PING`，~~感觉没有意义~~                      |
| testOnBorrow                      |    false    |     `false`     | 从 Pool 中获取的链接时候 `PING`，可以保证获取到的链接都是可用的，但是会影响性能，因为每次执行命令之前都要先 `PING` 一下 |
| **testWhileIdle**                 |    false    |     `true`      | 链接空闲的时候检查，仅在 `timeBetweenEvictionRunsMillis` 被设置成正值（ >0） 的时候才会生效 |
| testOnReturn                      |    false    |     `false`     | 把 Connection 归还给 Pool 的时候 `PING`                      |
|                                   |             |                 |                                                              |
| **timeBetweenEvictionRunsMillis** |     -1      | `30_000`(`30s`) | 空闲资源的检测周期（单位为毫秒），-1 不检查<br />把空闲时间超过`minEvictableIdleTimeMillis` 的连接断开，直到连接池中的连接数到`minIdle` 为止 |
| **minEvictableIdleTimeMillis**    | 180000(30m) |  `60_000`(1m)   | Pool 中资源的最小空闲时间（单位为毫秒），达到此值后空闲资源将被移除 |
| **numTestsPerEvictionRun**        |      3      |      `-1`       | 做空闲资源检测时，每次检测资源的个数。可根据自身应用连接数进行微调，如果设置为 -1，就是对所有连接做空闲监测 |
|                                   |             |                 |                                                              |
| jmxEnabled                        |    true     |     `true`      | 建议开启，请注意应用本身也需要开启                           |



## 关键参数设置建议

> 来自：阿里云  [JedisPool资源池优化 > 关键参数设置建议](https://help.aliyun.com/document_detail/98726.html#section-m2c-5kr-zfb)

### maxTotal（最大连接数）

想合理设置maxTotal（最大连接数）需要考虑的因素较多，如：

- 业务希望的 Redis 并发量；
- 客户端执行命令时间；
- Redis资源，例如 `应用个数等 * maxTotal` 不能超过 Redis 的最大连接数；
- 资源开销，例如虽然希望控制空闲连接，但又不希望因为连接池中频繁地释放和创建连接造成不必要的开销。

**假设一次命令时间，即 borrow ~ return resource 加上 Jedis 执行命令 （ 含网络耗时）的平均耗时约为 2ms，则一个连接的 QPS 大约是 500（1s / 2ms = 500），而业务期望的 QPS 是 5000，那么理论上需要的资源池大小是 5000 / 500 = 10**。

但事实上这只是个理论值，除此之外还要预留一些资源，所以 **maxTotal 可以比理论值大一些**。这个值不是越大越好，一方面连接太多会占用客户端和服务端资源，另一方面对于 Redis 这种高 QPS 的服务器，如果**出现大命令的阻塞，即使设置再大的资源池也无济于事**。

### maxIdle 与 minIdle

**maxIdle 设置 Pool 中大的空闲链接，保证下次流量高峰的时候可以立即有链接可用，所以 maxIdle 实际上才是业务需要的最大连接数**，maxTotal 是为了给出最大限制，所以 **maxIdle 不要设置得过小，否则会有 `new Jedis`（新连接）开销**，而 minIdle 是为了控制空闲资源检测。

**连接池的最佳性能是 maxTotal=maxIdle**，这样就避免了连接池伸缩带来的性能干扰。但如果并发量不大或者maxTotal 设置过高，则会导致不必要的连接资源浪费。

您可以根据实际总 QPS 和调用 Redis 的客户端规模整体评估每个节点所使用的连接池大小。

使用监控获取合理值

在实际环境中，比较可靠的方法是通过监控来尝试获取参数的最佳值。可以考虑通过 JMX 等方式实现监控，从而找到合理值。



如果 `minIdle = maxIdle = maxTotal` 则链接不会存在空闲回收、使用的时候创建新链接的情况，但是会浪费一定的资源，因为并发量的小的情况的，大部分链接是空闲的



如果资源不足，会看到以下异常 `redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool`



## Read More

- 阿里云 Redis 最佳实践 [JedisPool资源池优化](https://help.aliyun.com/document_detail/98726.html)
- - ❤ [Jedis常见异常汇总](https://help.aliyun.com/knowledge_detail/71967.html)
- 官方 Github [xetorthio/jedis](https://github.com/xetorthio/jedis) > [参数说明](https://help.aliyun.com/document_detail/98726.html#title-864-sx8-ria)