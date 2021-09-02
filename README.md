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
    <version>1.2.0</version>
    <type>pom</type>
</dependency>

<!--实现依赖-->
<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-tools-spring-boot-starter</artifactId>
  <version>1.2.0</version>
</dependency>

<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-log-spring-boot-starter</artifactId>
  <version>1.2.0</version>
</dependency>

<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-distribution-spring-boot-starter</artifactId>
  <version>1.2.0</version>
</dependency>

<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-sms-spring-boot-starter</artifactId>
  <version>1.2.0</version>
</dependency>

<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-mybatis-spring-boot-starter</artifactId>
  <version>1.2.0</version>
</dependency>

<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-activiti-spring-boot-starter</artifactId>
  <version>1.2.0</version>
</dependency>
```

### 使用说明

#### tools
```xml
<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-tools-spring-boot-starter</artifactId>
  <version>1.2.0</version>
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

#### sms
```xml
<dependency>
    <groupId>top.zopx</groupId>
    <artifactId>square-sms-spring-boot-starter</artifactId>
    <version>1.2.0</version>
</dependency>
```

配置
```yaml
square:
  sms:
    sms-li:
      open: true
      access-key-id: ddd
      access-secret: ddd
      sign-name: 批量无效
      region-id: 根据厂商指定
```

启动类添加
```java
@EnableSms
```

调用方法
```java
@Resource
private ISmsService smsService;

public void sendVer() throws Throwable {
    smsService.sendSms(
            SmsRequest.create()
                    .phoneNumber("********")
                    .template("短信末班|内容")
                    .signName("短信签名")
                    .templateParam("code", RandomUtils.nextLong(1000, 9999))
                    .builder(),
            System.out::println
    );
}
```

#### log

```xml
<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-log-spring-boot-starter</artifactId>
  <version>1.2.0</version>
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
  <version>1.2.0</version>
</dependency>
```

启动类
```java
@EnableActivitiDashboard
```

涉及接口在Activiti项目下README.md文件中

#### distribution

```xml
<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-distribution-spring-boot-starter</artifactId>
  <version>1.2.0</version>
</dependency>
```

启动类
```java
@EnableDistribution
```

包含单机、redis、zookeeper三种锁，单独配置

```java
@Distribution
public void lock1() {}


@Distribution(key = "'lock:'+#{name}")
public void lock2(String name) {}
```

