package top.zopx.goku.example.socket.gateway.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.common.constant.ServerTypeEnum;
import top.zopx.goku.example.socket.common.recognizer.MsgRecognizer;
import top.zopx.goku.example.socket.gateway.codec.SemiClientMsgFinished;
import top.zopx.goku.example.socket.gateway.router.AuthRoute;
import top.zopx.goku.example.socket.gateway.router.ChatRoute;
import top.zopx.goku.framework.socket.netty.constant.IKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端消息处理器
 *
 * @author Mr.Xie
 */
public class ClientMsgRoute extends ChannelInboundHandlerAdapter {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientMsgRoute.class);

    private final Map<IKey, ChannelInboundHandler> SERVER_TYPE_HANDLE_MAP = new ConcurrentHashMap<>();

    public ClientMsgRoute() {
        SERVER_TYPE_HANDLE_MAP.putIfAbsent(ServerTypeEnum.AUTH, new AuthRoute());
        SERVER_TYPE_HANDLE_MAP.putIfAbsent(ServerTypeEnum.CHAT, new ChatRoute());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof SemiClientMsgFinished)) {
            if (null != ctx) {
                ctx.fireChannelRead(msg);
            }
            return;
        }

        SemiClientMsgFinished semiClientMsg = (SemiClientMsgFinished) msg;

        final int msgCode = semiClientMsg.getMsgCode();

        // 获取当前服务器工作类型
        IKey currJobType = MsgRecognizer.getInstance().getServerTypeByMsgCode(msgCode);

        if (null == currJobType) {
            LOGGER.error(
                    "未识别出服务器工作类型, msgCode = {}",
                    msgCode
            );
            return;
        }

        // 获取处理器
        ChannelInboundHandler h = SERVER_TYPE_HANDLE_MAP.get(currJobType);

        if (null == h) {
            LOGGER.error(
                    "未找到可以处理 {} 类型消息的处理器, 请修改 {} 类默认构造器增加相关代码",
                    currJobType,
                    ClientMsgRoute.class.getName()
            );
            return;
        }

        try {
            h.channelRead(ctx, msg);
        } catch (Exception ex) {
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
