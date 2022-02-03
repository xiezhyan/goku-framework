package top.zopx.square.netty.util;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 群组功能
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/9/29
 */
public enum GroupKit {
    /**
     * 单例
     */
    INSTANCE,
    ;
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupKit.class);
    
    private static final Map<String, ChannelGroup> CHANNEL_GROUP_MAP = new ConcurrentHashMap<>(128);

    /**
     * 创建群组
     *
     * @param groupKey 群组Key
     */
    public void create(String groupKey) {
        if (StringUtils.isBlank(groupKey)) {
            return;
        }

        CHANNEL_GROUP_MAP.putIfAbsent(groupKey, new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
    }

    /**
     * 添加群成员
     *
     * @param groupKey 群组Key
     * @param channels 成员Channels
     */
    public void add(String groupKey, List<Channel> channels) {
        if (StringUtils.isBlank(groupKey)) {
            LOGGER.error("参数传递异常，不处理");
            return;
        }
        ChannelGroup channelGroup = CHANNEL_GROUP_MAP.getOrDefault(groupKey, new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
        channelGroup.addAll(channels);
        CHANNEL_GROUP_MAP.put(groupKey, channelGroup);
    }

    /**
     * 移除群成员
     *
     * @param groupKey 群组Key
     * @param channels 成员Channels
     */
    public void remove(String groupKey, List<Channel> channels) {
        if (StringUtils.isBlank(groupKey)) {
            LOGGER.error("参数传递异常，不处理");
            return;
        }
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
    public void remove(String groupKey) {
        if (StringUtils.isBlank(groupKey)) {
            LOGGER.error("参数传递异常，不处理");
            return;
        }
        CHANNEL_GROUP_MAP.remove(groupKey);
    }

    /**
     * 发送群组消息
     *
     * @param groupKey 群组Key
     * @param msg      消息
     */
    public void write(String groupKey, GeneratedMessageV3 msg) {
        if (StringUtils.isBlank(groupKey) || null == msg) {
            LOGGER.error("参数传递异常，不处理");
            return;
        }

        ChannelGroup channelGroup = CHANNEL_GROUP_MAP.get(groupKey);
        if (null == channelGroup) {
            LOGGER.error("当前群组Key：{}，不存在", groupKey);
            return;
        }

        channelGroup.writeAndFlush(msg);
    }

    /**
     * 群组发送消息
     *
     * @param groupKey    群组Key
     * @param msg         消息
     * @param channelKeys 指定发送
     */
    public void write(String groupKey, GeneratedMessageV3 msg, List<String> channelKeys) {
        if (StringUtils.isBlank(groupKey) || null == msg) {
            LOGGER.error("参数传递异常，不处理");
            return;
        }

        ChannelGroup channelGroup = CHANNEL_GROUP_MAP.get(groupKey);
        if (null == channelGroup) {
            LOGGER.error("当前群组Key：{}，不存在", groupKey);
            return;
        }

        // 指定客户端写出通知
        List<ChannelMatcher> list = new ArrayList<>();
        channelKeys.forEach(
                channelKey ->
                        ChannelKit.INSTANCE.getByKey(channelKey, Channel::isActive)
                                .forEach(channel -> list.add(ChannelMatchers.is(channel)))
        );

        channelGroup.writeAndFlush(msg, ChannelMatchers.compose(list.toArray(new ChannelMatcher[0])));
    }
}
