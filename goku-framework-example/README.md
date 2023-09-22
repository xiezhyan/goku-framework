## goku-framework-example
本项目为`goku-framework`示例项目，主要包括两部分
```text
├─goku-framework-example-socket
│  ├─doc
│  │  ├─conf
│  │  └─proto
│  ├─goku-framework-example-socket-biz
│  ├─goku-framework-example-socket-common
│  ├─goku-framework-example-socket-gateway
├─goku-framework-example-web
```

- `goku-framework-example-socket`是`goku-framework-socket`相关的示例，已实现业务服扩容处理，主要基于Netty + Redis订阅等方式实现。
> 随后需要从如下方面考虑`goku-framework-socket`的扩展
> 1. Redis订阅如何替换
> 2. 如MySQL，Redis等初始化配置如何操作
> 3. 单设备操作
> 4. 发送消息给其他人的操作

- `goku-framework-example-web`目前是关于`goku-framework-starter`相关的示例，基础配置，快速CRUD，国际化