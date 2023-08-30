package top.zopx.goku.example.socket.gateway.router;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.common.constant.ServerTypeEnum;
import top.zopx.goku.example.socket.common.entity.ClientInnerMsg;
import top.zopx.goku.example.socket.common.recognizer.MsgRecognizer;
import top.zopx.goku.example.socket.gateway.GatewayApp;
import top.zopx.goku.example.socket.gateway.codec.SemiClientMsgFinished;
import top.zopx.goku.framework.socket.core.util.Out;
import top.zopx.goku.framework.socket.netty.constant.IKey;
import top.zopx.goku.framework.socket.netty.runner.MultiServerRunner;
import top.zopx.goku.framework.socket.netty.selector.ServerSelector;
import top.zopx.goku.framework.socket.netty.util.IdUtil;
import top.zopx.goku.framework.socket.netty.selector.RouteTable;

/**
 * 随机选择业务端处理
 *
 * @author Mr.Xie
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
        // 最新版本号
        Out<Long> newRevOut = new Out<>();

        MultiServerRunner serverConn = ServerSelector.getServerConnByServerId(selectServerId, newRevOut);

        if (null == serverConn ||
                !serverConn.getReady() ||
                // 为了当扩容机器的时候，新机器也加入到被选择的行列
                rt.getRev(currJobType) != Out.get(newRevOut, -1L)
        ) {
            // 重新选择服务器
            serverConn =  ServerSelector.getServerConnByRandom(currJobType, newRevOut);
        }

        if (null == serverConn ||
                !serverConn.getReady()) {
            LOGGER.error(
                    "未找到合适的战绩服务器来接收消息, msgCode = {}",
                    msgCode
            );
            return;
        }

        rt.putServerIdAndRev(
                currJobType,
                serverConn.getServerId(),
                Out.get(newRevOut, -1L)
        );

        final ClientInnerMsg innerMsg = new ClientInnerMsg();
        innerMsg.setGatewayId(GatewayApp.getServerId());
        innerMsg.setRemoteSessionId(IdUtil.getSessionId(ctx));
        innerMsg.setFromUserId(IdUtil.getUserId(ctx));
        innerMsg.setMsgCode(msgCode);
        innerMsg.setData(semiClientMsg.getData());

        LOGGER.info(
                "转发消息到内部服务器, msgCode = {}, targetServer = {}",
                msgCode,
                serverConn.getServerName()
        );

        serverConn.send(innerMsg);

        // 释放资源
        semiClientMsg.free();
    }
}