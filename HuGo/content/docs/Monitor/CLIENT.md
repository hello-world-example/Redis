# Client 相关命令



## CLIENT LIST 

> http://www.redis.cn/commands/client-list.html

返回所有连接到服务器的客户端信息和统计数据

| 字段 | 描述 | 参考值 |
| :--: | ---- | :--: |
|**id**|客户端 ID|1645707|
|**addr**|客户端的地址和端口|172.16.2.85:53252|
|name|`CLIENT SETNAME` 设置的名称(设置成 `""` 删除设置)<br />`CLIENT GETNAME` 获取当前客户端名称||
|**age**|以秒计算的已连接时长|103|
|**idle**|以秒计算的空闲时长|103|
|**db**|该客户端正在使用的数据库 ID|0|
|sub|已订阅频道的数量|0|
|psub|已订阅频道的数量|0|
|**cmd**||ping|
|flags|O: 客户端是 MONITOR 模式下的附属节点（slave） <br />`S`: 客户端是一般模式下（normal）的附属节点 <br />`M`: 客户端是主节点（master） <br />x: 客户端正在执行事务 <br />b: 客户端正在等待阻塞事件 <br />i: 客户端正在等待 VM I/O 操作（已废弃） <br />d: 一个受监视（watched）的键已被修改， EXEC 命令将失败 <br />c: 在将回复完整地写出之后，关闭链接 <br />u: 客户端未被阻塞（unblocked） <br />U: 通过Unix套接字连接的客户端 <br />**`r` : 客户端是只读模式的集群节点** <br />A: 尽可能快地关闭连接 <br />N: 未设置任何 flag|N|
|...|...|...|

## CLIENT KILL

> http://www.redis.cn/commands/client-kill.html

```bash
# 指定链接
> CLIENT KILL ADDR addr:port

# 指定 ID
> CLIENT KILL ID client-id

# 指定类型 normal|slave|pubsub
> CLIENT KILL TYPE type

# 是否连同自己一块 Kill，默认 yes
> CLIENT KILL SKIPME yes|no
```



## CLIENT ID 

> http://www.redis.cn/commands/client-id.html

每个ID符合如下约束：

- **永不重复**。当调用命令 `CLIENT ID` 返回相同的值时，调用者可以确认原连接未被断开，只是被重用 ，因此仍可以认为是同一连接
- **ID 值单调递增**。若某一连接的 ID 值比其他连接的 ID 值大，可以确认该连接是较新创建的



## CLIENT PAUSE

> http://www.redis.cn/commands/client-pause.html

将所有客户端的访问暂停给定的毫秒数

- 停止处理所有来自一般客户端 或者 pub/sub 客户端的命令。但是和 slaves 的交互命令不受影响。
- 因为它会尽快返回 OK 给调用者，所以`CLIENT PAUSE` 不会把自己暂停。
- 当给定的时间结束，所有的客户端都被解除阻塞：查询缓存里积累的所有命令都会被处理。



## CLIENT UNBLOCK ??

> 起始版本：5.0.0
> http://www.redis.cn/commands/client-unblock.html

解除客户端的阻塞



## CLIENT REPLY

> 起始版本：3.2
> http://www.redis.cn/commands/client-reply.html

`CLIENT REPLY` `ON` | `OFF` | `SKIP` ，可设置服务器是否对客户端的命令进行回复。有如下选项：

- `ON`. 默认选项，回复客户端每条命令
- `OFF`. 不回复客户端命令
- `SKIP`. 跳过该命令的回复

