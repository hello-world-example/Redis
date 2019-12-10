# Redis Manager 监控指标

> https://github.com/ngbdf/redis-manager
>
> @see: [com.newegg.ec.redis.entity.NodeInfo](https://github.com/ngbdf/redis-manager/blob/master/redis-manager-dashboard/src/main/java/com/newegg/ec/redis/entity/NodeInfo.java)



| 指标 | 描述 | 来源 |
| :--- | :--- | :--- |
| **expires** | Key 的个数 |  |
| **keys** | 有过期时间的 Key 的个数 |  |
| blocked_clients |正在等待阻塞命令的客户端的数量<br />（[BLPOP](http://www.redis.cn/commands/blpop.html)、[BRPOP](http://www.redis.cn/commands/brpop.html)、[BRPOPLPUSH](http://www.redis.cn/commands/brpoplpush.html)）|INFO clients<br />blocked_clients|
| **connected_clients** |已连接客户端的数量|INFO clients<br />connected_clients|
| **[Bug]** commands_processed |处理的命令数|INFO Stats<br />total_commands_processed|
| **[Bug]** connections_received |创建连接个数，如果新创建连接过多，过度地创建和销毁连接对性能有影响，说明短连接严重或连接池使用有问题，需调研代码的连接设置|INFO Stats<br />total_connections_received|
| **instantaneous_ops_per_sec** |每秒处理的命令数|INFO Stats<br />instantaneous_ops_per_sec|
| **[Bug]** **keyspace_hits_ratio** |命中率<br />`keyspace_hits`/(`keyspace_hits`+`keyspace_misses`)|INFO Stats<br />keyspace_hits/keyspace_misses|
| rejected_connections |由于 `maxclients` 限制而拒绝的连接数|INFO Stats<br />rejected_connections|
| **----- Memory 相关 -----** |**----- Memory 相关 -----**|**----- Memory 相关 -----**|
| **used_memory** |分配器分配的内存|INFO Memory|
| **used_memory_rss** |操作系统的角度，已分配的内存总量（俗称常驻集大小）|INFO Memory|
| **used_memory_dataset** |数据集大小（used_memory减去used_memory_overhead）|INFO Memory|
| used_memory_overhead |服务器为管理其内部数据结构而分配的所有开销的总和|INFO Memory|
| **mem_fragmentation_ratio** | `used_memory_rss` 和 `used_memory` 之间的比率，`rss` > `used` ，且两者的值相差较大时，表示存在（内部或外部的）内存碎片 |INFO Memory|
| **----- CPU 相关 -----** | **----- CPU 相关 -----** |**----- CPU 相关 -----**|
| **[Bug]** cpu_sysd |Redis 服务器消耗的系统 CPU，单位秒|INFO CPU|
| **[Bug]** cpu_userd |Redis 服务器消耗的用户 CPU，单位秒|INFO CPU|
| **----- SYNC 相关 -----** |**----- SYNC 相关 -----**|**----- SYNC 相关 -----**|
| sync_full | 全量同步的次数（The number of full resyncs with replicas）  | INFO Stats |
| sync_partial_err               | 被拒绝的增量同步次数（The number of denied partial resync requests） | INFO Stats |
| sync_partial_ok                |被接受的增量同步次数（The number of accepted partial resync requests）|INFO Stats|



## Read More

- [Redis运行时的10大重要指标](https://mp.weixin.qq.com/s?__biz=MzU2NjIzNDk5NQ%3D%3D&mid=2247485579&idx=1&sn=c13dab19e9f0fae79df096fad42b42a9)
- sync_full - [Redis 2.8版部分同步功能源码浅析-Replication Partial Resynchronization](http://chenzhenianqing.com/articles/956.html)
- [Redis4.0新特性(三)-PSYNC2](https://blog.csdn.net/n88Lpo/article/details/78529195)