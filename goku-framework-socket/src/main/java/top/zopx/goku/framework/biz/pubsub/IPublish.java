package top.zopx.goku.framework.biz.pubsub;

/**
 * 发布
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/28
 */
public interface IPublish {

    /**
     * 发布
     *
     * @param channel 频道
     * @param msg     字符串消息
     */
    void pub(String channel, String msg);
}