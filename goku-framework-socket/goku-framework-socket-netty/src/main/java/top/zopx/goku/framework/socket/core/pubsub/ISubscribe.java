package top.zopx.goku.framework.socket.core.pubsub;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 订阅
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/04/28
 */
public interface ISubscribe {

    /**
     * 消息订阅处理
     * @param chArray 通道
     * @param h 消息处理器
     */
    default void subscribe(String[] chArray, ISubscribe h) {}

    /**
     * 消息订阅处理
     *
     * @param channel 信道
     * @param msg     订阅消息
     */
    default void onMsg(String channel, String msg) {}

    class SubscribeGroup implements ISubscribe {
        private final List<ISubscribe> subscribeList = new ArrayList<>();

        public SubscribeGroup add(ISubscribe handler) {
            if (null != handler) {
                this.subscribeList.add(handler);
            }
            return this;
        }

        @Override
        public void onMsg(String channel, String msg) {
            if (null == channel ||
                    null == msg) {
                return;
            }

            subscribeList.stream().filter(Objects::nonNull).forEach(handle -> handle.onMsg(channel, msg));
        }
    }
}