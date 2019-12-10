# SlowLog 慢查日志



## 获取 SlowLog 配置

```bash
> CONFIG GET slowlog*
1) "slowlog-log-slower-than"
2) "10000"
3) "slowlog-max-len"
4) "128"
```

## 设置 SlowLog 配置

```bash
> CONFIG SET slowlog-max-len 256
OK
```

## slowlog-log-slower-than

Redis 命令的执行分为以下 步骤 `发送`、`排队`、`执行`、`返回` ，slowlog 只统计 `执行` 阶段是时间，没有慢查询并不代表客户端没有超时。

- `slowlog-log-slower-than` 的单位是 微妙（1秒=1000000微秒），默认值 `10000 = 10ms`
  - `CONFIG SET slowlog-log-slower-than 0` 记录所有日志
  - `CONFIG SET slowlog-log-slower-than -1` 关闭慢查
- `slowlog-max-len` 慢查日志保存的队列长度，是个 FIFO 队列

## 获取慢查日志

```bash
# 获取2条慢查日志
> SLOWLOG GET 2
1) 1) (integer) 2544          # 唯一ID
   2) (integer) 1575910861    # 查询的时间戳
   3) (integer) 14919         # 查询耗时，微妙，即 14毫秒
   4) 1) "SETBIT"			        # 执行的命令			
      2) "test:key1"          # 命令参数
      3) "42753506"
      4) "1"
   5) "172.16.2.118:35329"    # 来源 IP (4.0+)
   6) ""											# 客户端名称，CLIENT SETNAME (4.0+)
2) 1) (integer) 2543
   2) (integer) 1575910801
   3) (integer) 20633
   4) 1) "SETBIT"
      2) "test:key2"
      3) "44996821"
      4) "1"
   5) "172.16.2.118:35329"
   6) ""
```



## Read More

- [SLOWLOG 官方文档](https://redis.io/commands/slowlog)
- [SLOWLOG 中文文档](http://www.redis.cn/commands/slowlog.html)
- [Redis高级功能 - 慢查询日志](https://segmentfault.com/a/1190000009915519)

