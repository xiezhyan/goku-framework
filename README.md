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
top.zopx:goku-framework-starter-material

top.zopx:goku-framework-support-mysql-binlog
top.zopx:goku-framework-support-primary
top.zopx:goku-framework-support-activiti

【暂未完成】top.zopx:goku-framework-starter-mq
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

## template.json格式
```json
[
  {
    "database": "",
    "tableList": [
      {
        "tableName": "",
        "insert": [
          {
            "column": ""
          }
        ],
        "update": [
          {
            "column": ""
          }
        ],
        "delete": [
          {
            "column": ""
          }
        ]
      }
    ]
  }
]
```

## Activiti
### 基本配置
```yaml
spring:
  datasource:
    # 使用druid数据源
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://192.168.86.200:12345/activiti?useUnicode=true&characterEncoding=utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8&useSSL=true&allowMultiQueries=true&autoReconnect=true&nullCatalogMeansCurrent=true
    username: root
    password: ZXCvbn789**..

  activiti:
    database-schema: activiti
    database-schema-update: true
    history-level: none
    check-process-definitions: false
```
### 启动配置
```java
@SpringBootApplication(
        exclude = {org.activiti.spring.boot.SecurityAutoConfiguration.class}
)
```