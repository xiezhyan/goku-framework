package top.zopx.goku.example.socket.gateway.router;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.common.constant.ServerTypeEnum;
import top.zopx.goku.example.socket.common.entity.ClientInnerMsg;
import top.zopx.goku.example.socket.common.recognizer.MsgRecognizer;
import top.zopx.goku.framework.util.IdUtil;
import top.zopx.goku.example.socket.gateway.GatewayApp;
import top.zopx.goku.example.socket.gateway.codec.SemiClientMsgFinished;
import top.zopx.goku.example.socket.gateway.selector.ServerSelector;
import top.zopx.goku.example.socket.gateway.sub.NewServerConnectSub;
import top.zopx.goku.framework.biz.constant.IKey;
import top.zopx.goku.framework.biz.selector.Client;
import top.zopx.goku.framework.util.RouteTable;

/**
 * 随机选择业务端处理
 *
 * @author 俗世游子
 */
public class ChatRoute extends ChannelInboundHandlerAdapter {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatRoute.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msgObj) {
        if (!(msgObj instanceof SemiClientMsgFinished)) {
            if (null != ctx) {
                ctx.fireChannelRead(msgObj);
            }

            return;
        }

        SemiClientMsgFinished semiClientMsg = (SemiClientMsgFinished) msgObj;

        final int msgCode = semiClientMsg.getMsgCode();

        // 获取当前服务器工作类型
        IKey currJobType = MsgRecognizer.getInstance().getServerTypeByMsgCode(msgCode);

        if (ServerTypeEnum.CHAT != currJobType) {
            LOGGER.error(
                    "当前命令不属于账户模块, msgCode = {}",
                    msgCode
            );
            return;
        }

        // 获取路由表
        RouteTable rt = RouteTable.getOrCreate(ctx);
        // 获取已经选择的服务器 Id
        int selectServerId = rt.getServerId(ServerTypeEnum.CHAT);

        Client serverConn = ServerSelector.getServerConnByServerId(
                NewServerConnectSub.getInstance(),
                selectServerId
        );

        if (null == serverConn ||
                !serverConn.isReady()) {
            // 重新选择服务器
            serverConn = ServerSelector.randomAServerConnByServerJobType(
                    NewServerConnectSub.getInstance(),
                    ServerTypeEnum.CHAT
            );
        }

        if (null == serverConn ||
                !serverConn.isReady()) {
            LOGGER.error(
                    "未找到合适的战绩服务器来接收消息, msgCode = {}",
                    msgCode
            );
            return;
        }

        rt.putServerId(ServerTypeEnum.CHAT, serverConn.getClientId());

        final ClientInnerMsg innerMsg = new ClientInnerMsg();
        innerMsg.setGatewayId(GatewayApp.getServerId());
        innerMsg.setRemoteSessionId(IdUtil.getSessionId(ctx));
        innerMsg.setFromUserId(IdUtil.getUserId(ctx));
        innerMsg.setMsgCode(msgCode);
        innerMsg.setData(semiClientMsg.getData());

        LOGGER.info(
                "转发消息到内部服务器, msgCode = {}, targetServer = {}",
                msgCode,
                serverConn.getClientName()
        );

        serverConn.sendMsg(innerMsg);

        // 释放资源
        semiClientMsg.free();
    }
}