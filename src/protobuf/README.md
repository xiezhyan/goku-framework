
# 基于protobufjs将proto文件转换为js/ts文件

```shell
npm install protobufjs-cli -g

pbjs -t static-module -w commonjs -o message.js message.proto
pbts -o message.d.ts message.js
```

# websocket基于protobuf的使用
## 自定义协议

- 消息长度
- 消息编码
- 消息体

## 登录操作

1. websocket连接不需要特定场景，任意地方都能操作`MsgBus.getInstance().putServerAddr().tryInit()`， 链接地址通过`import.meta.env.VITE_WEBSOCKET_URL`获取
2. 用户进入登录页面之后，需要进行如下操作
   1. ping等常规操作
   2. login&checkInTicket
   3. 收到消息前端直接重写`MsgBus.getInstance().onMsgHandler = () => {}`方法，已在MsgBus中对消息进行处理
3. 其他页面如何处理接收到的消息
   1. 添加协议内容，类似`msg/common.All`的操作，同时调用`MsgRecognizer.addProtocol()`
   2. 注册消息事件，也就是需要重写`MsgBus.getInstance().onMsgHandler = () => {}`的方式

举个例子:
```ts
import { MsgBus } from "./protobuf/websocket/MsgBus";
import { common } from "./protobuf/msg/common";

let pingId = 0
MsgBus.getInstance()
  .putServerAddr(import.meta.env.VITE_WEBSOCKET_URL)
  .tryInit();

setInterval(() => {
  // 断线检查
  disconnCheck();
  // ping
  ping();
}, 3000)

const disconnCheck = () => {
  if(MsgBus.getInstance().isReady()) {
    return
  }

  if (MsgBus.getInstance().getConnTryCount() >= 3) {
    console.info('连接已断开~');
    return
  }

  // 尝试重新初始化
  let oFuture = MsgBus.getInstance().tryInit();

  if (null != oFuture) {
      oFuture.onOpen = () => {
          // 执行重新连接逻辑 UKey或其他
      }

      oFuture.onError = () => {
        console.info('重新连接失败~');
      }
  }
}

const ping = () => {
  if(!MsgBus.getInstance().isReady()) {
    return
  }

  MsgBus.getInstance().sendMsg(
    common.CommonDef._PingRequest,
    common.PingRequest.create({pingId: pingId++})
  );
}

// 消息处理
const g_oResultHandlerMap: { [strKey: string]: (oParam: any) => void} = {
    "PingResponse": __handleOnPingResponse__,
};

function __handleOnPingResponse__(res: common.PingResponse) {
    // 弹窗显示等异常信息
    console.info("ping response ", res.pingId)
}

MsgBus.getInstance()
  .onMsgHandler = (strMsgClazzName: string, oMsgBody: any) => {
      if(!(strMsgClazzName in g_oResultHandlerMap)) {
        return 
      }
      
      g_oResultHandlerMap[strMsgClazzName](oMsgBody);
  }
```

