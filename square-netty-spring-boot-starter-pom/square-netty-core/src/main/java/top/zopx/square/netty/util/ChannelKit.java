package top.zopx.square.netty.util;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.commons.collections4.CollectionUtils;
import top.zopx.square.netty.configurator.constant.AttributeKeyConstant;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Channel相关操作
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/9/13
 */
public final class ChannelKit {

    private final AtomicInteger idGen = new AtomicInteger(0);

    private final List<Channel> EMPTY_LIST;
    private final Multimap<String, Channel> SESSION_MAP;
    private final transient ChannelFutureListener listener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) {
            future.removeListener(this);
            remove(future.channel());
        }
    };

    private ChannelKit() {
        EMPTY_LIST = new LinkedList<>();
        SESSION_MAP = Multimaps.synchronizedMultimap(LinkedListMultimap.create());
    }

    private static class Holder {
        public static final ChannelKit INSTANCE = new ChannelKit();
    }

    public static ChannelKit getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 通过当前Channel获取绑定用户
     *
     * @param channel 通道
     * @return ID
     */
    public String getUserIdByChannel(Channel channel) {
        return channel.attr(AttributeKeyConstant.USER_ATTR).get();
    }

    /**
     * 通过ID得到绑定的channel
     *
     * @param id 用户ID
     * @return Collection<Channel>
     */
    public Collection<Channel> getById(String id) {
        return SESSION_MAP.get(id);
    }

    /**
     * 通过ID得到绑定的channel
     *
     * @param id 用户ID
     * @return Channel
     */
    public Optional<Channel> getFirstById(String id) {
        return getById(id).stream().findFirst();
    }

    /**
     * 通过ID得到绑定的channel
     *
     * @param id        用户ID
     * @param predicate 过滤条件
     * @return Collection<Channel>
     */
    public Collection<Channel> getById(String id, Predicate<Channel> predicate) {
        return getById(id).stream().filter(predicate).collect(Collectors.toList());
    }


    /**
     * 通过channel获取其他连接通道
     *
     * @param channel 通道
     * @return Collection<Channel>
     */
    public Collection<Channel> getById(Channel channel) {
        String id = this.getUserIdByChannel(channel);
        if (id != null) {
            return SESSION_MAP.get(id);
        }
        return EMPTY_LIST;
    }

    /**
     * 添加到管理
     * 1、先获取绑定用户，保证用户和当前通道符合正确
     * 2、将Channel添加到SESSION_MAP中，并且由于是多客户端，可能会存在当前账户多个Channel
     *
     * @param channel 通道
     */
    public void put(Channel channel) {
        String id = this.getUserIdByChannel(channel);
        if (id != null && channel.isActive()) {
            channel.closeFuture().addListener(this.listener);
            SESSION_MAP.put(id, channel);
        }

        if (!channel.isActive()) {
            this.remove(channel);
        }
    }

    /**
     * 将当前通道从SESSION_MAP中移除
     * 1、如果多客户端全部下线，那么从SESSION_MAP中删除当前记录
     *
     * @param channel 通道
     */
    public void remove(Channel channel) {
        String id = this.getUserIdByChannel(channel);
        if (id != null) {
            SESSION_MAP.remove(id, channel);
            if (CollectionUtils.isEmpty(getById(id))) {
                SESSION_MAP.removeAll(id);
            }
        }
    }

    /**
     * 移除Channel
     *
     * @param key       标识
     * @param predicate 过滤条件
     */
    public void remove(String key, Predicate<Channel> predicate) {
        this.getById(key).stream().filter(predicate).forEach(this::close);
    }

    /**
     * 关闭通道
     *
     * @param channel 通道
     */
    public void close(Channel channel) {
        channel.close();
    }

    /**
     * 将消息发送到全部客户端
     *
     * @param channel 指定用户
     * @param msg     消息
     */
    public void write(Channel channel, GeneratedMessageV3 msg) {
        write(channel, msg, Channel::isActive);
    }

    /**
     * 将消息发送到指定客户端
     *
     * @param channel   指定用户
     * @param msg       消息
     * @param predicate 过滤器
     */
    public void write(Channel channel, GeneratedMessageV3 msg, Predicate<Channel> predicate) {
        this.getById(channel).stream().filter(predicate).forEach(client -> writeToLoop(client, msg));
    }

    /**
     * 通过Key将消息发送到指定客户端
     *
     * @param key       指定用户
     * @param msg       消息
     * @param predicate 过滤器
     */
    public void write(String key, GeneratedMessageV3 msg, Predicate<Channel> predicate) {
        this.getById(key, predicate).forEach(client -> writeToLoop(client, msg));
    }

    public void writeToLoop(Channel channel, GeneratedMessageV3 msg) {
        channel.eventLoop().execute(() -> channel.writeAndFlush(msg));
    }

    /**
     * 设置sessionID
     *
     * @param channel 通道
     */
    public void setSessionID(Channel channel) {
        if (null != channel) {
            setSessionID(channel, idGen.incrementAndGet());
        }
    }

    /**
     * 得到sessionID
     *
     * @param channel 通道
     * @return sessionID
     */
    public int getSessionID(Channel channel) {
        if (null == channel) {
            return -1;
        }

        return channel.attr(AttributeKeyConstant.SESSION_ID).get();
    }

    /**
     * 自定义sessionID
     *
     * @param channel   通道
     * @param sessionId sessionID
     */
    public void setSessionID(Channel channel, int sessionId) {
        if (null != channel) {
            channel.attr(AttributeKeyConstant.SESSION_ID).setIfAbsent(sessionId);
        }
    }
}
