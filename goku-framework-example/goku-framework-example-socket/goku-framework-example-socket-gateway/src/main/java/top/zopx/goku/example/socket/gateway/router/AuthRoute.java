package top.zopx.goku.example.socket.gateway.router;

import com.google.gson.JsonObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.common.constant.ServerTypeEnum;
import top.zopx.goku.example.socket.common.entity.ClientInnerMsg;
import top.zopx.goku.example.socket.common.recognizer.MsgRecognizer;
import top.zopx.goku.example.socket.gateway.GatewayApp;
import top.zopx.goku.example.socket.gateway.codec.SemiClientMsgFinished;
import top.zopx.goku.example.socket.gateway.handle.ClientMsgHandleMsg;
import top.zopx.goku.example.socket.proto.auth.Auth;
import top.zopx.goku.framework.socket.netty.constant.IKey;
import top.zopx.goku.framework.socket.netty.runner.MultiServerRunner;
import top.zopx.goku.framework.socket.netty.selector.ServerSelector;
import top.zopx.goku.framework.socket.netty.util.IdUtil;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

import java.net.InetSocketAddress;

/**
 * 随机选择业务端处理
 *
 * @author Mr.Xie
 */
public class AuthRoute extends ChannelInboundHandlerAdapter {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRoute.class);

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

        if (ServerTypeEnum.AUTH != currJobType) {
            LOGGER.error(
                    "当前命令不属于账户模块, msgCode = {}",
                    msgCode
            );
            return;
        }

        // 获取服务器连接, 也就是本机到目标服务器的连接
        // 登录过程都是随机选择一个服务器
        MultiServerRunner serverConn = ServerSelector.getServerConnByRandom(currJobType);

        if (null == serverConn ||
                !serverConn.getReady()) {
            LOGGER.error(
                    "未找到合适的登陆服务器来接收消息, msgCode = {}",
                    msgCode
            );
            return;
        }

        LOGGER.info(
                "转发消息到内部服务器, msgCode = {}, targetServer = {}",
                msgCode,
                serverConn.getServerName()
        );

        if (semiClientMsg.getMsgCode() == Auth.AuthDef._LoginRequest_VALUE) {
            // 如果是用户登陆命令,
            // 执行补充逻辑
            supplyUserLoginCmd(ctx, semiClientMsg);
        }

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

    /**
     * 补充用户登陆消息, 例如: clientIpAddr
     *
     * @param ctx       信道处理器上下文
     * @param clientMsg 客户端消息
     */
    private static void supplyUserLoginCmd(ChannelHandlerContext ctx, SemiClientMsgFinished clientMsg) {
        if (null == ctx ||
                null == clientMsg) {
            return;
        }

        try {
            // 获取客户端 IP 地址
            String clientIpAddr = ClientMsgHandleMsg.getXRealIp(ctx);

            if (null == clientIpAddr) {
                InetSocketAddress socketAddr = (InetSocketAddress) ctx.channel().remoteAddress();
                clientIpAddr = socketAddr.getAddress().getHostAddress();
            }
            LOGGER.info("client ip = {}", clientIpAddr);

            // 解析为用户登陆命令
            final Auth.LoginRequest loginRequest = Auth.LoginRequest.parseFrom(clientMsg.getData());
            JsonObject jsonObj = GsonUtil.getInstance().toObject(loginRequest.getLogin(), JsonObject.class);
            jsonObj.addProperty("clientIp", clientIpAddr);

//            // 修改消息体字节数组
            clientMsg.setData(
                    loginRequest.newBuilderForType()
                            .setLoginType(loginRequest.getLoginType())
                            .setLogin(jsonObj.toString())
                            .build().toByteArray()
            );
        } catch (Exception ex) {
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
        }
    }

}