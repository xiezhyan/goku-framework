package top.zopx.goku.example.socket.gateway.selector;

import com.google.gson.JsonObject;
import io.netty.channel.ChannelHandler;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.common.constant.Constant;
import top.zopx.goku.example.socket.common.util.ReadFileUtil;
import top.zopx.goku.example.socket.gateway.handle.ClientMsgHandle;
import top.zopx.goku.example.socket.gateway.sub.NewServerConnectSub;
import top.zopx.goku.framework.biz.constant.IKey;
import top.zopx.goku.framework.biz.pubsub.ISubscribe;
import top.zopx.goku.framework.biz.redis.RedisCache;
import top.zopx.goku.framework.biz.redis.RedisSubscribe;
import top.zopx.goku.framework.biz.selector.Client;
import top.zopx.goku.framework.cluster.constant.PublishCons;
import top.zopx.goku.framework.cluster.constant.ServerCommandLineEnum;
import top.zopx.goku.framework.netty.bind.entity.ServerAcceptor;
import top.zopx.goku.framework.netty.bind.entity.WebsocketClient;
import top.zopx.goku.framework.netty.bind.factory.BaseChannelHandlerFactory;
import top.zopx.goku.framework.netty.server.NettyServerAcceptor;
import top.zopx.goku.framework.tools.util.string.StringUtil;
import top.zopx.goku.framework.util.Out;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public final class ServerSelector {

    private ServerSelector() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerSelector.class);

    public static Client randomAServerConnByServerJobType(NewServerConnectSub newServerConnectSub, IKey currJobType) {
        return randomAServerConnByServerJobType(
                newServerConnectSub, currJobType, null
        );
    }

    private static Client randomAServerConnByServerJobType(NewServerConnectSub newServerConnectSub, IKey currJobType, Out<Long> out) {
        if (null == currJobType) {
            return null;
        }

        // 创建一个临时列表
        List<Client.ServerProfile> tempList = null;

        for (Client.ServerProfile sp : newServerConnectSub.getServerProfileList()) {
            if (null == sp ||
                    null == sp.getClient() ||
                    !sp.getClient().isReady()) {
                continue;
            }

            if (sp.getServerJobTypeSet().contains(currJobType.getType())) {
                if (null == tempList) {
                    tempList = new ArrayList<>();
                }

                tempList.add(sp);
            }
        }

        if (null == tempList ||
                tempList.isEmpty()) {
            LOGGER.error(
                    "服务器列表为空, expectJobType = {}",
                    currJobType
            );
            return null;
        }

        // 选择一个服务器资料
        int selectedIndex = RandomUtils.nextInt(0, tempList.size());
        Client.ServerProfile profile = tempList.get(selectedIndex);

        // 设置版本号
        return profile.getClient();
    }

    public static Client getServerConnByServerId(NewServerConnectSub instance, int selectServerId) {
        return getServerConnByServerId(
                instance, selectServerId, null
        );
    }

    private static Client getServerConnByServerId(NewServerConnectSub instance, int selectServerId, Out<Long> out) {
        // 获取服务器资料
        Client.ServerProfile profile = instance.getServerProfileById(selectServerId);

        if (null == profile ||
                null == profile.getClient() ||
                !profile.getClient().isReady()) {
            return null;
        }

        return profile.getClient();
    }
}
