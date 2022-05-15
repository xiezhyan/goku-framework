package top.zopx.square.netty.util;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.square.netty.configurator.constant.AttributeKeyConstant;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Channel相关操作
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/9/13
 */
public final class ChannelBizUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelBizUtil.class);

    private final AtomicInteger idGen = new AtomicInteger(0);

    private final List<Channel> EMPTY_LIST;
    private final Multimap<Long, Channel> SESSION_MAP;

    private final transient ChannelFutureListener listener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) {
            future.removeListener(this);
            remove(future.channel());
        }
    };

    private ChannelBizUtil() {
        EMPTY_LIST = new LinkedList<>();
        SESSION_MAP = Multimaps.synchronizedMultimap(LinkedListMultimap.create());
    }

    private static class Holder {
        public static final ChannelBizUtil INSTANCE = new ChannelBizUtil();
    }

    public static ChannelBizUtil getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 通过当前Channel获取绑定用户
     *
     * @param channel 通道
     * @return ID
     */
    public Long getUserIdByChannel(Channel channel) {
        return ChannelUtil.get(channel, AttributeKeyConstant.USER_ATTR);
    }

    /**
     * 给Channel设置UserID
     *
     * @param channel
     * @param id
     */
    public void setUserIdToChannel(Channel channel, long id) {
        if (Objects.isNull(channel) || id < 0) {
            return;
        }

        channel.attr(AttributeKeyConstant.USER_ATTR).setIfAbsent(id);
    }

    /**
     * 设置sessionID
     *
     * @param channel 通道
     */
    public void setSessionId(Channel channel) {
        if (null != channel) {
            setSessionId(channel, idGen.incrementAndGet());
        }
    }

    /**
     * 得到sessionID
     *
     * @param channel 通道
     * @return sessionID
     */
    public int getSessionId(Channel channel) {
        if (null == channel) {
            return -1;
        }

        return Optional.ofNullable(ChannelUtil.get(channel, AttributeKeyConstant.SESSION_ID)).orElse(-1);
    }

    /**
     * 自定义sessionID
     *
     * @param channel   通道
     * @param sessionId sessionID
     */
    public void setSessionId(Channel channel, int sessionId) {
        if (null != channel) {
            channel.attr(AttributeKeyConstant.SESSION_ID).setIfAbsent(sessionId);
        }
    }

    /**
     * 添加到管理
     * 1、先获取绑定用户，保证用户和当前通道符合正确
     * 2、将Channel添加到SESSION_MAP中，并且由于是多客户端，可能会存在当前账户多个Channel
     *
     * @param channel  通道
     * @param consumer 如果存在同样客户端 那么后续操作
     */
    public void putChannelToMap(Channel channel, Consumer<Channel> consumer) {
        Long id = this.getUserIdByChannel(channel);
        if (id != null && channel.isActive()) {
            // 是否存在终端编号
            if (!channel.hasAttr(AttributeKeyConstant.PLATFORM)) {
                LOGGER.error("当前channel没有设置终端编号， channel = {}", channel);
                return;
            }

            // 是否已经存在当前类似客户端
            final Optional<Channel> oldChannel = SESSION_MAP.get(id)
                    .stream()
                    .filter(ch -> channel.attr(AttributeKeyConstant.PLATFORM).equals(ch.attr(AttributeKeyConstant.PLATFORM)))
                    .findFirst();

            if (oldChannel.isEmpty()) {
                channel.closeFuture().addListener(this.listener);
                SESSION_MAP.put(id, channel);
                return;
            }

            this.remove(oldChannel.get());
            if (Objects.nonNull(consumer)) {
                consumer.accept(oldChannel.get());
            }
        }
    }

    /**
     * 通过用户ID得到绑定的channel
     *
     * @param id 用户ID
     * @return Collection<Channel>
     */
    public Collection<Channel> getChannelByUserId(Long id) {
        return SESSION_MAP.get(id);
    }

    /**
     * 通过ID得到绑定的channel
     *
     * @param id        用户ID
     * @param predicate 过滤条件
     * @return Collection<Channel>
     */
    public Collection<Channel> getChannelByUserId(Long id, Predicate<Channel> predicate) {
        return getChannelByUserId(id).stream().filter(predicate).toList();
    }


    /**
     * 通过channel获取其他连接通道
     *
     * @param channel 通道
     * @return Collection<Channel>
     */
    public Collection<Channel> getAllChannelByChannel(Channel channel) {
        Long id = this.getUserIdByChannel(channel);
        if (id != null) {
            return SESSION_MAP.get(id);
        }
        return EMPTY_LIST;
    }


    /**
     * 将当前通道从SESSION_MAP中移除
     * 1、如果多客户端全部下线，那么从SESSION_MAP中删除当前记录
     *
     * @param channel 通道
     */
    public void remove(Channel channel) {
        Long id = this.getUserIdByChannel(channel);
        if (id != null) {
            SESSION_MAP.remove(id, channel);
            if (CollectionUtils.isEmpty(getChannelByUserId(id))) {
                SESSION_MAP.removeAll(id);
            }
        }
    }

    /**
     * 向当前通道写出消息
     *
     * @param channel 通道
     * @param msg     消息
     */
    public void write(Channel channel, GeneratedMessageV3 msg) {
        ChannelUtil.writeToLoop(channel, msg);
    }

    /**
     * 向指定通道写出消息
     *
     * @param channel   通道
     * @param msg       消息
     * @param predicate 指定条件
     */
    public void write(Channel channel, GeneratedMessageV3 msg, Predicate<Channel> predicate) {
        getChannelByUserId(
                getUserIdByChannel(channel), predicate
        ).forEach(ch -> write(ch, msg));
    }

    /**
     * 指定设备发送消息
     *
     * @param channel   通道
     * @param msg       消息
     * @param platforms 设备号
     */
    @SuppressWarnings("all")
    public void write(Channel channel, GeneratedMessageV3 msg, int... platforms) {
        getAllChannelByChannel(channel).stream().filter(ch ->
                List.of(platforms).contains(ch.attr(AttributeKeyConstant.PLATFORM).get())
        ).forEach(ch -> write(ch, msg));
    }
}
