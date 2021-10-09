package top.zopx.netty.handle;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.Channel;

/**
 * 基础业务处理类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/9/13
 */
public interface ICmdHandler<T extends GeneratedMessageV3> {

    /**
     * 操作类
     *
     * @param channel 上下文对象
     * @param cmd     消息体
     */
    void cmd(Channel channel, T cmd);

}
