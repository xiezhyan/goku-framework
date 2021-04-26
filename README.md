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
    <version>1.1.5</version>
    <type>pom</type>
</dependency>

<!--实现依赖-->
<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-tools-spring-boot-starter</artifactId>
  <version>1.1.5</version>
</dependency>

<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-log-spring-boot-starter</artifactId>
  <version>1.1.5</version>
</dependency>

<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-distribution-spring-boot-starter</artifactId>
  <version>1.1.5</version>
</dependency>

<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-sms-spring-boot-starter</artifactId>
  <version>1.1.5</version>
</dependency>

<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-mybatis-spring-boot-starter</artifactId>
  <version>1.1.5</version>
</dependency>

<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>square-activiti-spring-boot-starter</artifactId>
  <version>1.1.5</version>
</dependency>
```