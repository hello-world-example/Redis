# INFO 命令

- `server`: Redis服务器的一般信息
- `clients`: 客户端的连接部分
- `memory`: 内存消耗相关信息
- `persistence`: RDB 和 AOF 相关信息
- `stats`: 一般统计
- `replication`: 主/从复制信息
- `cpu`: 统计CPU的消耗
- `commandstats`: Redis命令统计
- `cluster`: Redis集群信息
- `keyspace`: 数据库的相关统计



## Server ✔️

| 指标               | 描述                                                 | 参考值                                |
| ------------------ | ---------------------------------------------------- | ------------------------------------- |
| **redis_version**  | 版本                                                 | 5.0.4                                 |
| redis_build_id     | 构建 ID                                              | 2325020596d7902e                      |
| **redis_mode**     | 服务器模式                                           | `standalone`、 `sentinel`、 `cluster` |
| os                 | Redis 服务器的宿主操作系统                           |                                       |
| arch_bits          | 架构（32 或 64 位）                                  | 64                                    |
| multiplexing_api   | Redis 使用的事件循环机制                             | epoll                                 |
| atomicvar_api      | Redis 使用的 Atomicvar API                           | atomic-builtin                        |
| gcc_version        | 编译 Redis 时所使用的 GCC 版本                       |                                       |
| **process_id**     | 进程的 PID                                           |                                       |
| run_id             | 标识Redis服务器的随机值（将由Sentinel和Cluster使用） | cff1f.{45,45}                         |
| **tcp_port**       | 监听端口                                             |                                       |
| uptime_in_seconds  | 自 Redis 服务器启动以来，经过的秒数                  |                                       |
| **uptime_in_days** | 自 Redis 服务器启动以来，经过的天数                  |                                       |
| **hz**             | serverCron 频率设置，默认 10，即 10/s                | 10                                    |
| configured_hz      | 同上                                                 | 10                                    |
| lru_clock          | 时钟每分钟递增，用于LRU管理                          | 15663059                              |
| executable         | Redis 文件的路径                                     | /xx/xx/bin/redis-server               |
| config_file        | Redis 配置文件路径                                   | /xx/xx/redis.conf                     |

> - hz - [关于Redis中的 serverCron](https://www.cnblogs.com/chenpingzhao/archive/2016/02/24/5211456.html)
> - lru_clock - [Redis内存回收:LRU算法](https://www.cnblogs.com/WJ5888/p/4371647.html)



## Clients ✔️

| 指标                            | 描述                                                         | 参考值 |
| ------------------------------- | ------------------------------------------------------------ | ------ |
| **connected_clients**           | 已连接客户端的数量（不包括通过从属服务器连接的客户端）       |        |
| client_recent_max_input_buffer  | longest output list among current client connections         |        |
| client_recent_max_output_buffer | biggest input buffer among current client connections        |        |
| **blocked_clients**             | 正在等待阻塞命令的客户端的数量<br />（[BLPOP](http://www.redis.cn/commands/blpop.html)、[BRPOP](http://www.redis.cn/commands/brpop.html)、[BRPOPLPUSH](http://www.redis.cn/commands/brpoplpush.html)） | 0      |



## Memory ✔️

| 指标                          | 描述                                                         | 参考值         |
| ----------------------------- | ------------------------------------------------------------ | -------------- |
| **used_memory**               | Redis使用其分配器（标准libc，jemalloc或其他分配器，例如tcmalloc）分配的字节总数 | 49334616       |
| **used_memory_human**         | 同上                                                         | 47.05M         |
| **used_memory_rss**           | 从操作系统的角度，返回 Redis 已分配的内存总量（俗称常驻集大小），这是诸如 `top` 和 `ps` 之类的工具报告的数字 | 99725312       |
| **used_memory_rss_human**     | 同上                                                         | 95.11M         |
| **used_memory_peak**          | Redis 的内存消耗峰值（以字节为单位）                         | 50136424       |
| **used_memory_peak_human**    | 同上                                                         | 47.81M         |
| used_memory_peak_perc         | 使用内存占峰值内存的百分比                                   | 98.40%         |
| used_memory_overhead          | 服务器为管理其内部数据结构而分配的所有开销的总和（以字节为单位） | 4479300        |
| used_memory_startup           | Redis在启动时消耗的初始内存大小（以字节为单位）              | 1463240        |
| **used_memory_dataset**       | 数据集大小（used_memory减去used_memory_overhead），以字节为单位 | 44,855,316     |
| **used_memory_dataset_perc**  | used_memory_dataset 占净内存使用量的百分比（used_memory 减 used_memory_startup） | 93.70%         |
| allocator_allocated           | ~~官方未解释~~                                               | 49598712       |
| allocator_active              | ~~官方未解释~~                                               | 51957760       |
| allocator_resident            | ~~官方未解释~~                                               | 57466880       |
| **total_system_memory**       | Redis 主机具有的内存总量                                     | 4018974720     |
| **total_system_memory_human** | 同上                                                         | 3.74G          |
| used_memory_lua               | 引擎所使用的内存大小（以字节为单位）                         | 37888          |
| used_memory_lua_human         | 同上                                                         | 37.00K         |
| used_memory_scripts           | 缓存的 Lua 脚本使用的字节数                                  | 0              |
| used_memory_scripts_human     | 同上                                                         | 0B             |
| number_of_cached_scripts      | ~~官方未解释~~ 缓存的 Lua 脚本个数                           | 0              |
| maxmemory                     | 配置的最大内存大小                                           | 0              |
| maxmemory_human               | 同上                                                         | 0B             |
| **maxmemory_policy**          | 淘汰策略                                                     | noeviction     |
| allocator_frag_ratio          | ~~官方未解释~~                                               | 1.05           |
| allocator_frag_bytes          | ~~官方未解释~~                                               | 2359048        |
| allocator_rss_ratio           | ~~官方未解释~~                                               | 1.11           |
| allocator_rss_bytes           | ~~官方未解释~~                                               | 5509120        |
| rss_overhead_ratio            | ~~官方未解释~~                                               | 1.74           |
| rss_overhead_bytes            | ~~官方未解释~~                                               | 42258432       |
| mem_fragmentation_ratio       | `used_memory_rss` 和 `used_memory` 之间的比率                | 2.00           |
| mem_fragmentation_bytes       | ~~官方未解释~~                                               | 50431712       |
| mem_not_counted_for_evict     | ~~官方未解释~~                                               | 0              |
| mem_replication_backlog       | ~~官方未解释~~                                               | 1048576        |
| mem_clients_slaves            | ~~官方未解释~~                                               | 0              |
| mem_clients_normal            | ~~官方未解释~~                                               | 168148         |
| mem_aof_buffer                | ~~官方未解释~~                                               | 0              |
| mem_allocator                 | 在编译时指定的， Redis 所使用的内存分配器。可以是 libc 、 jemalloc 或者 tcmalloc | jemalloc-5.1.0 |
| active_defrag_running         | 碎片整理是否正在运行                                         | 0              |
| lazyfree_pending_objects      | 等待释放的对象数                                             | 0              |

> - 在理想情况下， `used_memory_rss` 的值应该只比 `used_memory` 稍微高一点儿。当 `rss` > `used` ，且两者的值相差较大时，表示存在（内部或外部的）内存碎片。内存碎片的比率可以通过 `mem_fragmentation_ratio` 的值看出。
>
> - 当 used > rss 时，表示 Redis 的部分内存被操作系统换出到交换空间了，在这种情况下，操作可能会产生明显的延迟。由于Redis无法控制其分配的内存如何映射到内存页，因此常住内存（used_memory_rss）很高通常是内存使用量激增的结果。
> - 当 Redis 释放内存时，内存将返回给分配器，分配器可能会，也可能不会，将内存返还给操作系统。如果 Redis 释放了内存，却没有将内存返还给操作系统，那么 used_memory 的值可能和操作系统显示的 Redis 内存占用并不一致。查看 used_memory_peak 的值可以验证这种情况是否发生。
>
> 要获得有关服务器内存的其他内省信息，可以参考 [`MEMORY STATS`](https://redis.io/commands/memory-stats) 和 [`MEMORY DOCTOR`](https://redis.io/commands/memory-doctor)。

## Persistence ✔️

| 指标 | 描述                                                         | 参考值 |
| ---- | ---- | ---- |
|loading|否正在进行 转储文件（dump）的加载|0|
|changes_since_last_save|上次调用 `SAVE` 或者 `BGSAVE` 以来，在数据集中产生变化的操作的数量||
|**RDB**|-----|-----|
|rdb_changes_since_last_save|自上次转储以来的更改次数|8281|
|rdb_bgsave_in_progress|RDB 文件是否正在保存|0|
|rdb_last_save_time|上次成功保存 RDB 的时间戳|1575914342|
|rdb_last_bgsave_status|上次RDB保存操作的状态|ok|
|rdb_last_bgsave_time_sec|上次RDB保存操作的持续时间（以秒为单位）|1|
|rdb_current_bgsave_time_sec|正在进行的RDB保存操作的持续时间|-1|
|rdb_last_cow_size|上次 RDB 保存操作期间 copy-on-write 分配的字节大小|9605120|
|**AOF**|-----|-----|
|aof_enabled|是否正在 AOF|0|
|aof_rewrite_in_progress|是否正在 AOF|0|
|aof_rewrite_scheduled|RDB 保存操作完成，是否安排进行 AOF|0|
|aof_last_rewrite_time_sec|上次 AOF 重写操作的持续时间，以秒为单位|-1|
|aof_current_rewrite_time_sec|正在进行的AOF重写操作的持续时间|-1|
|aof_last_bgrewrite_status|上次 AOF 重写操作的状态|ok|
|aof_last_write_status|上次 AOF 写入操作的状态|ok|
|aof_last_cow_size|上次 AOF 重写操作期间 copy-on-write 分配的字节大小|0|
|**如果启用了 AOF**|-----|-----|
|**aof_current_size**|当前的AOF文件大小||
|aof_base_size|上次启动或重写时的AOF文件大小||
|aof_pending_rewrite|指示AOF重写操作是否会在当前RDB保存操作完成后立即执行的标志||
|aof_buffer_length|AOF 缓冲区大小||
|aof_rewrite_buffer_length|AOF 重写缓冲区大小||
|aof_pending_bio_fsync|在后台 IO 队列中等待 fsync 处理的任务数||
|aof_delayed_fsync|延迟 fsync 计数器||
|**正在执行加载操作**|-----|-----|
|loading_start_time|加载操作的开始时间||
|loading_total_bytes|文件总大小||
|loading_loaded_bytes|已经加载的字节数||
|**loading_loaded_perc**|已经加载的百分比||
|**loading_eta_seconds**|预计加载完成所需的剩余秒数||


## Stats ✔️
| 指标 | 描述                                                         | 参考值 |
| ---- | ---- | ---- |
|total_connections_received|服务器 接收/创建 的连接总数|1508279|
|total_commands_processed|服务器处理的命令总数|46205630|
|**instantaneous_ops_per_sec**|每秒处理的命令数|1|
|total_net_input_bytes|网络入口流量字节数|45628947259|
|total_net_output_bytes|网络出口流量字节数|21817965728|
|instantaneous_input_kbps|网络入口 kps|0.03|
|instantaneous_output_kbps|网络出口 kps|0.53|
|**rejected_connections**|由于 `maxclients` 限制而拒绝的连接数|0|
|sync_full|全量同步的次数（The number of full resyncs with replicas）|1|
|sync_partial_ok|被接受的增量同步次数（The number of accepted partial resync requests）|0|
|sync_partial_err|被拒绝的增量同步次数（The number of denied partial resync requests）|1|
|expired_keys|key 到期事件的总数|59102|
|*expired_stale_perc*|~~官方未未解释~~|0.00|
|*expired_time_cap_reached_count*|~~官方未未解释~~|0|
|**evicted_keys**|由于 `maxmemory` 限制而导致被驱逐的key的数量|0|
|**keyspace_hits**|在主字典中成功查找到 key 的次数|1395688|
|**keyspace_misses**|在主字典中查找 key 失败的次数|8885993|
|pubsub_channels|拥有客户端订阅的全局 pub/sub 通道数|0|
|pubsub_patterns|拥有客户端订阅的全局 pub/sub 模式数|0|
|**latest_fork_usec**|最新 fork 操作的持续时间，以**微秒**为单位|5372|
|migrate_cached_sockets| |0|
|slave_expires_tracked_keys||0|
|active_defrag_hits||0|
|active_defrag_misses||0|
|active_defrag_key_hits||0|
|active_defrag_key_misses||0|

> sync_full - [Redis 2.8版部分同步功能源码浅析-Replication Partial Resynchronization](http://chenzhenianqing.com/articles/956.html)

## Replication ✔️

| 指标 | 描述                                                         | 参考值 |
| ---- | ---- | ---- |
|role|如果实例不是任何节点的从节点，则值是”master”，如果实例从某个节点同步数据，则是”slave”，一个从节点可以是另一个从节点的主节点|slave|
|master_host|主节点的 Host名称或 IP地址|172.16.2.119|
|master_port|主节点的Host名称或IP地址|6383|
|master_link_status|master 连接状态（up或者down）|up|
|master_last_io_seconds_ago|同步延时，单位秒|1|
|master_sync_in_progress|是否正在同步|0|
|master_sync_left_bytes|同步完成前剩余的字节数||
|master_sync_last_io_seconds_ago|同步操作期间自上次传输IO以来的秒数||
|master_link_down_since_seconds|主从断开的秒数||
|connected_slaves|已连接 从节点个数||
|slave_repl_offset|副本复制偏移量|44845771223|
|slave_priority|故障转移候选者的优先级|100|
|slave_read_only|副本是否被标记为只读|1|
|connected_slaves|链接的副本数|0|
|master_replid|复制 ID||
|master_replid2|同步失败后的备用复制 ID||
|master_repl_offset|The server's current replication offset|44845771223|
|second_repl_offset|The offset up to which replication IDs are accepted|9072049241|
|repl_backlog_active|Flag indicating replication backlog is active|1|
|repl_backlog_size|Total size in bytes of the replication backlog buffer|1048576|
|repl_backlog_first_byte_offset|The master offset of the replication backlog buffer|44844722648|
|repl_backlog_histlen|Size in bytes of the data in the replication backlog buffer|1048576|

> [Redis4.0新特性(三)-PSYNC2](https://blog.csdn.net/n88Lpo/article/details/78529195)

## CPU ✔️

> CPU 消耗率计算公式 = ( ( `used_cpu_sys_now` - `used_cpu_sys_before` ) / ( `now` - `before`) ) * 100
>
> `used_cpu_sys_now` : now 时间点的 `used_cpu_sys` 值
>
> `used_cpu_sys_before` : before 时间点的 `used_cpu_sys` 值

| 指标 | 描述                                         | 参考值（单位 秒） |
| ---- | ---- | ---- |
|used_cpu_sys|内核态 所消耗的 CPU 时间（累计值）|6866.187180|
|used_cpu_user|用户态 消耗的CPU时间（累计值）|9999.339869|
|used_cpu_sys_children|后台进程指令，内核态 消耗的CPU时间（累计值）|869.472820|
|used_cpu_user_children|后台进程指令，用户态 消耗的CPU时间（累计值）|4437.289429|

## Cluster  ✔️
| 指标 | 描述                | 参考值 |
| ---- | ---- | ---- |
|cluster_enabled|是否已启用Redis集群|1|

## Keyspace  ✔️
> dbXXX: keys=XXX,expires=XXX,avg_ttl=XXX

| 指标    | 描述                    | 参考值        |
| ------- | ----------------------- | ------------- |
|         | 第几个DB                | db0           |
| keys    | Key 的个数              | 20243         |
| expires | 有过期时间的 Key 的个数 | 19410         |
| avg_ttl | 平均过期时间            | 1167346935000 |



## Read More

- [io/commands/info](https://redis.io/commands/info)
- [cn/commands/info.html](http://www.redis.cn/commands/info.html)
- [如何监控Redis的工作状态——INFO命令详解](http://ghoulich.xninja.org/2016/10/15/how-to-monitor-redis-status/)