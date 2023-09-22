package top.zopx.goku.framework.socket.netty.selector;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import top.zopx.goku.framework.socket.netty.constant.IKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 路由表, 主要是记录客户端到具体的业务服务器 Id
 *
 * @author Mr.Xie
 * @date 2022/2/4
 * @email xiezhyan@126.com
 */
public final class RouteTable {
    /**
     * 会话主键
     */
    public static final String SESSION_KEY_ROUTE_TABLE = "route_table";

    /**
     * 已经选择的服务器字典
     */
    private static final Map<IKey, SelectedServer> SELECTED_SERVER_MAP = new ConcurrentHashMap<>(64);


    /**
     * 类默认构造器
     */
    private RouteTable() {
    }

    /**
     * 获取路由表, 如果为空就创建
     *
     * @param ctx 信道处理器上下文
     * @return 路由表
     */
    public static RouteTable getOrCreate(ChannelHandlerContext ctx) {
        if (null != ctx) {
            return getOrCreate(ctx.channel());
        } else {
            return null;
        }
    }

    /**
     * 获取路由表, 如果为空就创建
     *
     * @param ch 客户端信道
     * @return 路由表
     */
    public static RouteTable getOrCreate(Channel ch) {
        if (null == ch) {
            return null;
        }

        // 获取属性关键字
        AttributeKey<RouteTable> aKey = AttributeKey.valueOf(SESSION_KEY_ROUTE_TABLE);
        // 获取路由表
        RouteTable rt = ch.attr(aKey).get();

        if (null == rt) {
            ch.attr(aKey).setIfAbsent(new RouteTable());
        }

        return ch.attr(aKey).get();
    }

    /**
     * 根据服务器工作类型获取服务器 Id
     *
     * @param currJobType 服务器工作类型
     * @return 服务器 Id
     */
    public int getServerId(IKey currJobType) {
        if (null == currJobType) {
            return -1;
        }

        // 获取已经选择的服务器
        SelectedServer selServer = SELECTED_SERVER_MAP.get(currJobType);
        return (null == selServer) ? -1 : selServer.getServerId();
    }

    /**
     * 根据服务器工作类型获取版本号,
     * 也就是路由表中记录的关于当前服务器工作类型的版本号
     *
     * @param currJobType 服务器工作类型
     * @return 版本号
     */
    public long getRev(IKey currJobType) {
        if (null == currJobType) {
            return -1;
        }

        // 获取已经选择的服务器
        SelectedServer selServer = SELECTED_SERVER_MAP.get(currJobType);
        return (null == selServer) ? -1L : selServer.getRev();
    }

    /**
     * 设置服务器 Id
     *
     * @param currJobType 服务器工作类型
     * @param serverId    服务器 Id
     */
    public void putServerId(IKey currJobType, int serverId) {
        putServerIdAndRev(currJobType, serverId, -1L);
    }

    /**
     * 设置服务器 Id 和版本号
     *
     * @param currJobType 服务器工作类型
     * @param serverId    服务器 Id
     * @param rev         版本号
     */
    public void putServerIdAndRev(IKey currJobType, int serverId, long rev) {
        if (null == currJobType) {
            return;
        }

        SELECTED_SERVER_MAP.put(
                currJobType,
                new SelectedServer(serverId, rev)
        );
    }

    /**
     * 移除服务器 Id 和版本号
     *
     * @param currJobType 服务器工作类型
     */
    public void removeServerIdAndRev(IKey currJobType) {
        if (null != currJobType) {
            SELECTED_SERVER_MAP.remove(currJobType);
        }
    }

    /**
     * 已经选择的服务器
     */
    private record SelectedServer(int serverId, long rev) {

        /**
         * 获取服务器 Id
         *
         * @return 服务器 Id
         */
        public int getServerId() {
            return serverId;
        }

        /**
         * 获取版本号
         *
         * @return 版本号
         */
        public long getRev() {
            return rev;
        }
    }
}
