package top.zopx.goku.framework.socket.core.server.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import top.zopx.goku.framework.socket.core.constant.AttributeKeyCons;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端 channel
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/04/27
 */
public final class GatewayUtil {

    private GatewayUtil() {
    }

    /**
     * gatewayId, Channel
     */
    private static final Map<Integer, Channel> GATEWAY_CHANNEL_MAP = new ConcurrentHashMap<>(64);


    /**
     * 设置网关ID
     *
     * @param ctx       通道
     * @param gatewayId 网关ID
     */
    public static void putGatewayId(ChannelHandlerContext ctx, int gatewayId) {
        Channel ch = ctx.channel();
        if (null != ch) {
            ch.attr(AttributeKeyCons.GATEWAY_SERVER_ID).setIfAbsent(gatewayId);
        }
    }

    /**
     * 获取网关ID
     *
     * @param ctx 通道
     * @return 网关ID
     */
    public static int getGatewayId(ChannelHandlerContext ctx) {
        Channel ch = ctx.channel();
        if (null == ch) {
            return -1;
        }

        // 获取 Id
        Integer xId = ch.attr(AttributeKeyCons.GATEWAY_SERVER_ID).get();

        if (null == xId) {
            return -1;
        } else {
            return xId;
        }
    }

    public static Channel getByGatewayId(int gatewayId) {
        return GATEWAY_CHANNEL_MAP.get(gatewayId);
    }

    public static void add(ChannelHandlerContext ctx) {
        if (null == ctx) {
            return;
        }

        Channel ch = ctx.channel();
        if (null == ch) {
            return;
        }

        int gatewayId = getGatewayId(ctx);
        GATEWAY_CHANNEL_MAP.put(gatewayId, ch);
    }

    public static void remove(ChannelHandlerContext ctx) {
        if (null == ctx) {
            return;
        }

        int gatewayId = getGatewayId(ctx);
        GATEWAY_CHANNEL_MAP.remove(gatewayId);
    }
}
