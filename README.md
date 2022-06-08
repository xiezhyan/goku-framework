## goku-framework
够快才够酷
## 使用

```text
top.zopx:goku-framework-dependency

top.zopx:goku-framework-tools
top.zopx:goku-framework-socket
top.zopx:goku-framework-starter-web
top.zopx:goku-framework-starter-log
top.zopx:goku-framework-starter-cache
top.zopx:goku-framework-starter-mybatis
top.zopx:goku-framework-starter-mysql-binlog
top.zopx:goku-framework-starter-material
top.zopx:goku-framework-starter-mq
top.zopx:goku-framework-starter-process
top.zopx:goku-framework-starter-schedule
```

## mysql-binlog

### binlog记录模式
- statement：基于SQL语句的模式，某些语句和函数如UUID, LOAD DATA INFILE等在复制过程可能导致数据不一致甚至出错。
- row：基于行的模式，记录的是行的变化，很安全。但是binlog会比其他两种模式大很多，在一些大表中清除大量数据时在binlog中会生成很多条语句，可能导致从库延迟变大。
- mixed：混合模式，根据语句来选用是statement还是row模式

### 开启MySQL的binlog
```
[mysqld]
#设置记录模式
binlog_format = mixed

# 设置日志路径，注意路经需要mysql用户有权限写
log-bin = /data/mysql/logs/mysql-bin.log

# 设置binlog清理时间
expire_logs_days = 7

# binlog每个日志文件大小
max_binlog_size = 100m

# binlog缓存大小
binlog_cache_size = 4m

# 最大binlog缓存大小
max_binlog_cache_size = 512m
```

> 验证： `SHOW MASTER LOGS`

