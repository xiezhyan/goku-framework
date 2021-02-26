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
  <artifactId>square-boot-starter</artifactId>
  <version>1.1.4</version>
  <type>pom</type>
</dependency>

<!--实现依赖-->
<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>tools-boot-starter</artifactId>
  <version>1.1.4</version>
</dependency>

<dependency>
  <groupId>top.zopx</groupId>
  <artifactId>sms-boot-starter</artifactId>
  <version>1.1.4</version>
</dependency>
```

