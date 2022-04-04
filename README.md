# square-boot-starter

### 介绍
针对项目中重复代码进行分离，将重复代码如：工具类，Redis，短信，邮件等抽离成单独的模块



### 针对系统

该代码针对**SpringBoot**系统，内部包含：

- SpringBoot依赖
- SpringCloud依赖
- 各种工具依赖等

> 持续更新中

### 依赖

```xml
<!--parent-->
<dependency>
    <groupId>top.zopx</groupId>
    <artifactId>square-spring-boot-starter</artifactId>
    <version>1.4.0</version>
    <type>pom</type>
</dependency>

<!--实现依赖-->
<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-tools-spring-boot-starter</artifactId>
  <version>1.4.0</version>
</dependency>

<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-log-spring-boot-starter</artifactId>
  <version>1.4.0</version>
</dependency>

<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-mybatis-spring-boot-starter</artifactId>
  <version>1.4.0</version>
</dependency>

<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-activiti-spring-boot-starter</artifactId>
  <version>1.4.0</version>
</dependency>
```

### 使用说明

#### tools
```xml
<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-tools-spring-boot-starter</artifactId>
  <version>1.4.0</version>
</dependency>
```

该依赖中包含：
- 基本返回对象
- 常用注解类
- 常用加密类【持续更新】
- 通用异常类
- 基本工具类方法
    - 浏览器
    - copy对象
    - json
    - 反射
    - 字符串
    - web相关
    - ID生成

#### log

```xml
<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-log-spring-boot-starter</artifactId>
  <version>1.4.0</version>
</dependency>
```

```yaml
square:
  log:
    endurance: true
```

```java
class A implement ILogService {}
```

#### Activiti

```xml
<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-activiti-spring-boot-starter</artifactId>
  <version>1.4.0</version>
</dependency>
```

启动类
```java
@EnableActivitiUI
```

涉及接口在Activiti项目下README.md文件中

#### Netty

```xml
<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-netty-spring-boot-starter</artifactId>
  <version>1.4.0</version>
</dependency>

<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-netty-core</artifactId>
  <version>1.4.0</version>
  <exclusions>
    <exclusion>
      <groupId>org.springframework.boot</groupId>
      <artifactId>*</artifactId>
    </exclusion>
    <exclusion>
      <groupId>org.hibernate.validator</groupId>
      <artifactId>hibernate-validator</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```

启动类
```java
@EnableNettyCore
```

```java
@Resource
private NettyProperties nettyProperties;

@Bean(destroyMethod = "destory")
public NettyServerAcceptor nettyServerAccepter() {
    return new NettyServerAcceptor.Builder()
            .setApp(nettyProperties.getApp())
            .setWs(nettyProperties.getWs())
            .setWriteTimeout(nettyProperties.getWriteTimeout())
            .setReadTimeout(nettyProperties.getReadTimeout())
            .setFactory(new ChannelHandlerFactoryImpl_0())
            .build();
}
```