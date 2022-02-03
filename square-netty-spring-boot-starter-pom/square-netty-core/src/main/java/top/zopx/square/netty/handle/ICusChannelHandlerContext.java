package top.zopx.square.netty.handle;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 俗世游子
 * @date 2022/2/4
 * @email xiezhyan@126.com
 */
public interface ICusChannelHandlerContext {

    void setUserId(String userId);

    String getUserId();

    long getRemoteSessionId();

    void setRemoteSessionId(long sessionId);

    int getServerId();

    void setServerId(int serverId);

    void writeAndFlush(Object msg);

    default JSONObject getExtra() {
        return null;
    }

    default void setExtra(JSONObject jsonObj){}
}
