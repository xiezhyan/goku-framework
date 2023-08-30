package top.zopx.goku.framework.socket.core.cmd.context;

import io.netty.channel.ChannelFuture;

/**
 * 基础业务处理类
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2021/9/13
 */
public abstract class BaseCmdHandleContext {

    /**
     * 代理服务器 Id
     */
    private int gatewayServerId;

    /**
     * 远程会话 Id
     */
    private int remoteSessionId = -1;

    /**
     * 客户端 IP
     */
    private String clientIp = null;

    /**
     * 来自用户 Id
     */
    private long fromUserId = -1L;

    public int getGatewayServerId() {
        return gatewayServerId;
    }

    public BaseCmdHandleContext setGatewayServerId(int gatewayServerId) {
        this.gatewayServerId = gatewayServerId;
        return this;
    }

    public int getRemoteSessionId() {
        return remoteSessionId;
    }

    public BaseCmdHandleContext setRemoteSessionId(int remoteSessionId) {
        this.remoteSessionId = remoteSessionId;
        return this;
    }

    public String getClientIp() {
        return clientIp;
    }

    public BaseCmdHandleContext setClientIp(String clientIp) {
        this.clientIp = clientIp;
        return this;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public BaseCmdHandleContext setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
        return this;
    }

    /**
     * 写出错误
     *
     * <pre>
     * {@code
     * // 示例代码
     *  public ChannelFuture sendError(int errorCode, String errorMsg) {
     *         CommProtocol.ErrorHintResult.Builder b = CommProtocol.ErrorHintResult.newBuilder();
     *         b.setErrorCode(errorCode);
     *         b.setErrorMsg(Objects.requireNonNullElse(errorMsg, ""));
     *         return writeAndFlush(b.build());
     *     }
     * }
     * </pre>
     *
     * @param errorCode 错误代码
     * @param errorMsg  错误消息
     * @return 信道预期
     */
    public abstract ChannelFuture send(int errorCode, String errorMsg);

    /**
     * 写出消息
     *<pre>
     * {@code
     * // 示例代码
     * public ChannelFuture writeAndFlush(Object msgObj) {
     *         if (!(msgObj instanceof GeneratedMessageV3) ||
     *             null == _proxyServerCh ||
     *             !_proxyServerCh.isWritable()) {
     *             return null;
     *         }
     *
     *         // 获取协议消息
     *         GeneratedMessageV3 protobufMsg = (GeneratedMessageV3) msgObj;
     *
     *         InternalServerMsg innerMsg = new InternalServerMsg()
     *             .setProxyServerId(getProxyServerId())
     *             .setRemoteSessionId(getRemoteSessionId())
     *             .setClientIP(getClientIP())
     *             .setFromUserId(getFromUserId())
     *             .setMsgCode(MsgRecognizer.getMsgCodeByMsgClazz(protobufMsg.getClass()))
     *             .setMsgBody(protobufMsg.toByteArray());
     *
     *         return _proxyServerCh.writeAndFlush(innerMsg);
     *     }
     * }
     *</pre>
     * @param msgObj 消息对象
     * @return 信道预期
     */
    public abstract ChannelFuture writeAndFlush(Object msgObj);

}
