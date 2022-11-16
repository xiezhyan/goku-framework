package top.zopx.goku.example.socket.biz.context;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import top.zopx.goku.example.socket.common.entity.ClientInnerMsg;
import top.zopx.goku.example.socket.common.recognizer.MsgRecognizer;
import top.zopx.goku.example.socket.proto.common.Common;
import top.zopx.goku.framework.netty.bind.handler.BaseCmdHandleContext;

/**
 * 基础业务处理类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/9/13
 */
public class CmdHandleContext extends BaseCmdHandleContext {

    private final Channel channel;

    public CmdHandleContext(Channel channel) {
        this.channel = channel;
    }

    /**
     * 写出错误
     *
     * @param errorCode 错误代码
     * @param errorMsg  错误消息
     * @return 信道预期
     */
    public ChannelFuture sendError(int errorCode, String errorMsg) {
        Common.Response response = Common.Response.newBuilder()
                .setMsg(errorMsg)
                .setCode(errorCode)
                .build();

        return writeAndFlush(response);
    }

    /**
     * 写出消息
     *
     * @param msgObj 消息对象
     * @return 信道预期
     */
    public ChannelFuture writeAndFlush(Object msgObj) {
        if (!(msgObj instanceof GeneratedMessageV3 messageV3) ||
                null == channel ||
                !channel.isWritable()) {
            return null;
        }

        ClientInnerMsg innerMsg = new ClientInnerMsg();
        innerMsg.setGatewayId(getGatewayServerId());
        innerMsg.setRemoteSessionId(getRemoteSessionId());
        innerMsg.setClientIp(getClientIp());
        innerMsg.setFromUserId(getFromUserId());
        innerMsg.setMsgCode(MsgRecognizer.getInstance().getCodeByMsgObj(messageV3.getClass()));
        innerMsg.setData(messageV3.toByteArray());
        return channel.writeAndFlush(innerMsg);
    }
}
