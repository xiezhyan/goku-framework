package top.zopx.goku.framework.util;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.ChannelMatchers;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.tools.exceptions.BusException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 群组功能
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/9/29
 */
public final class GroupUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupUtil.class);

    private static final Map<String, ChannelGroup> CHANNEL_GROUP_MAP = new ConcurrentHashMap<>(128);

    static void noting(String groupKey) {
        if (StringUtils.isBlank(groupKey) ) {
            throw new BusException("groupKey为空");
        }
    }

    /**
     * 创建群组
     *
     * @param groupKey 群组Key
     */
    public static void create(String groupKey) {
        noting(groupKey);
        CHANNEL_GROUP_MAP.putIfAbsent(groupKey, new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
    }

    /**
     * 添加群成员
     *
     * @param groupKey 群组Key
     * @param channels 成员Channels
     */
    public static void add(String groupKey, List<Channel> channels) {
        noting(groupKey);

        if (CHANNEL_GROUP_MAP.containsKey(groupKey)) {
            CHANNEL_GROUP_MAP.get(groupKey).addAll(channels);
        } else {
            DefaultChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            CHANNEL_GROUP_MAP.put(groupKey, channelGroup);
        }
    }

    /**
     * 移除群成员
     *
     * @param groupKey 群组Key
     * @param channels 成员Channels
     */
    public static void remove(String groupKey, List<Channel> channels) {
        noting(groupKey);

        ChannelGroup channelGroup = CHANNEL_GROUP_MAP.get(groupKey);
        if (null == channelGroup) {
            LOGGER.error("当前群组Key：{}，不存在", groupKey);
            return;
        }

        channels.forEach(channelGroup::remove);
    }

    /**
     * 解散群
     *
     * @param groupKey 群组Key
     */
    public static void remove(String groupKey) {
        noting(groupKey);

        ChannelGroup remove = CHANNEL_GROUP_MAP.remove(groupKey);
        if (null != remove) {
            remove.clear();
            remove.close();
        }
    }

    /**
     * 发送群组消息
     *
     * @param groupKey 群组Key
     * @param msg      消息
     */
    public static void write(String groupKey, GeneratedMessageV3 msg, Function<ChannelGroup, List<ChannelMatcher>> function) {
        noting(groupKey);

        ChannelGroup channelGroup = CHANNEL_GROUP_MAP.get(groupKey);
        if (null == channelGroup) {
            LOGGER.error("当前群组Key：{}，不存在", groupKey);
            return;
        }

        if (null != function) {
            List<ChannelMatcher> matchers = function.apply(channelGroup);
            channelGroup.writeAndFlush(msg, ChannelMatchers.compose(matchers.toArray(new ChannelMatcher[0])));
            return;
        }

        channelGroup.writeAndFlush(msg);
    }
}
