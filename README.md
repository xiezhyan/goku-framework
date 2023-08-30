[中文](./README.md) | [English](./README.en.md)

## goku-framework

![logo](./doc/logo.png)

### 简介

SpringBoot时代下极大的简化了整个系统的搭建，而**goku-framework**能够更快的处理整个开发流程下的问题。

作为基础架构层的存在：

- 对依赖版本进行严格控制，并且覆盖进行自定义版本
- 基于Netty的网络开发，分为单机版本和集群版本，对标游戏架构，简化处理中的疑难业务
- 日志信息怎么存储随你来定，CRUD快到不适应
- 内置Activiti可视化流程图，分布式ID，binlog解析等方案。
- ...

### 架构

```text
├─goku-framework-dependency                           // 依赖管理      
├─goku-framework-socket                               // Netty网络开发      
├─goku-framework-starter                              //               
│  ├─goku-framework-starter-cache                     // Redis基本配置                
│  ├─goku-framework-starter-log                       // 日志处理              
│  ├─goku-framework-starter-material                  // MinIO，阿里OSS                    
│  ├─goku-framework-starter-mybatis                   // CRUD操作                  
│  └─goku-framework-starter-web                       // SpringBoot开发最小单元 
├─goku-framework-support                              //           
│  ├─goku-framework-support-activiti                  // Activiti工作流     
│  ├─goku-framework-support-mysql-binlog              // MySQL binlog解析   
│  └─goku-framework-support-primary                   // 分布式ID           
├─goku-framework-tools                                // 核心类库        
```

### 使用

关于整个架构如何使用，关注示例项目

- `goku-framework-example`

更多详细介绍，关注博客：

- [Mr.Xie【Mr.Xie】的掘金](https://juejin.cn/user/3359725700263694)
- [Mr.Xie【Mr.Xie】的51CTO](https://blog.51cto.com/u_14948012)
- 微信公众号、B站搜索「谢先生说技术」

> 基于`goku-framework`架构的[享阅读II](https://gitee.com/mr_sanq/enjoy-read-ii)正在火热研发中！！！

### 特别提供

如果大家采用EasyCode的话，基于`goku-framework`的代码生成配置可以帮助到大家

[EasyCode基础配置](./doc/EasyCodeConfig.json)

## 扩展

### mysql-binlog

#### binlog记录模式

- statement：基于SQL语句的模式，某些语句和函数如UUID, LOAD DATA INFILE等在复制过程可能导致数据不一致甚至出错。
- row：基于行的模式，记录的是行的变化，很安全。但是binlog会比其他两种模式大很多，在一些大表中清除大量数据时在binlog中会生成很多条语句，可能导致从库延迟变大。
- mixed：混合模式，根据语句来选用是statement还是row模式

#### 开启MySQL的binlog

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

### goku-framework-support-mysql-binlog

#### template.json格式

> 将需要处理的字段已如下格式进行配置，方便解析处理

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

### Activiti

#### 内置接口

[接口文档点击查看](./doc/activiti-api.md)

#### 基本配置

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

#### 启动配置

```java
@SpringBootApplication(
        exclude = {org.activiti.spring.boot.SecurityAutoConfiguration.class}
)
```
