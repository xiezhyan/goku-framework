package top.zopx.goku.framework.socket.core.pubsub;

/**
 * 发布
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/04/28
 */
public interface IPublish {

    /**
     * 发布
     *
     * @param topic 频道
     * @param msg     字符串消息
     */
    void pub(String topic, String msg);
}