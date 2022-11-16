package top.zopx.goku.example.socket.gateway;

import com.google.gson.JsonObject;
import io.netty.channel.ChannelHandler;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.common.constant.Constant;
import top.zopx.goku.example.socket.common.util.ReadFileUtil;
import top.zopx.goku.framework.biz.ukey.UKey;
import top.zopx.goku.example.socket.gateway.handle.ClientMsgHandle;
import top.zopx.goku.example.socket.gateway.sub.NewServerConnectSub;
import top.zopx.goku.framework.biz.pubsub.ISubscribe;
import top.zopx.goku.framework.biz.redis.RedisCache;
import top.zopx.goku.framework.biz.redis.RedisSubscribe;
import top.zopx.goku.framework.cluster.constant.PublishCons;
import top.zopx.goku.framework.cluster.constant.ServerCommandLineEnum;
import top.zopx.goku.framework.netty.bind.entity.ServerAcceptor;
import top.zopx.goku.framework.netty.bind.entity.WebsocketClient;
import top.zopx.goku.framework.netty.bind.factory.BaseChannelHandlerFactory;
import top.zopx.goku.framework.netty.server.NettyServerAcceptor;
import top.zopx.goku.framework.tools.util.string.StringUtil;

import java.time.Duration;
import java.util.Map;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public class GatewayApp implements BaseChannelHandlerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayApp.class);

    private static int serverId;
    private static String serverName;
    private static String serverIp;
    private static int serverPort;

    public static void main(String[] args) {

        /**
         * --server_id=1001
         * --server_name=proxy_server_1001
         * --server_job_type_set=PASSPORT,HALL,GAME,CLUB,CHAT,RECORD
         * -h 0.0.0.0
         * -p 20480
         * -c ../etc/proxyserver_all.conf.json
         */
        final Map<String, String> commandLnMap = ServerCommandLineEnum.createCommandLine(args);
        if (MapUtils.isEmpty(commandLnMap)) {
            LOGGER.error("缺少必要的系统参数");
            return;
        }

        serverId = StringUtil.toInteger(commandLnMap.get(ServerCommandLineEnum.SERVER_ID.getLongOpt()));
        serverName = commandLnMap.get(ServerCommandLineEnum.SERVER_NAME.getLongOpt());
        serverIp = commandLnMap.get(ServerCommandLineEnum.SERVER_HOST.getLongOpt());
        serverPort = StringUtil.toInteger(commandLnMap.get(ServerCommandLineEnum.SERVER_PORT.getLongOpt()));

        // 读取配置文件
        JsonObject configObj = ReadFileUtil.read(commandLnMap.get(ServerCommandLineEnum.CONFIG.getLongOpt()));
        if (null == configObj) {
            LOGGER.error("必要配置项缺失");
            return;
        }

        // redis初始化
        final RedisCache.Config redisConf = RedisCache.Config.fromJsonData(configObj);
        if (null != redisConf) {
            RedisCache.init(redisConf);
        }

        final UKey.Config config = UKey.Config.fromJsonData(configObj);
        if (null != config) {
            UKey.init(config);
        }

        startGatewayServerApp();
        startSubServer();
    }

    private static void startGatewayServerApp() {
        new NettyServerAcceptor(
                ServerAcceptor.create()
                        .setBossThreadPool(2)
                        .setWorkThreadPool(8)
                        .setFactory(new GatewayApp())
                        .setReadTimeout(Duration.ofSeconds(45L))
                        .setWriteTimeout(Duration.ofSeconds(60L))
                        .setWs(
                                WebsocketClient.create()
                                        .setHost(serverIp)
                                        .setPort(serverPort)
                                        .setPath(Constant.WEBSOCKET_PATH)
                                        .build()
                        )
                        .build()
        ).start();
        LOGGER.info("--> 网关服务:{} 已启动 <--", serverName);
    }

    private static void startSubServer() {
        String[] channelArr = {
                PublishCons.REGISTER_SERVER
        };

        ISubscribe.SubscribeGroup group = new ISubscribe.SubscribeGroup();
        group.add(NewServerConnectSub.getInstance());

        new RedisSubscribe().subscribe(channelArr, group);
    }


    @Override
    public ChannelHandler createWebsocketMsgHandler() {
        return new ClientMsgHandle();
    }

    public static int getServerId() {
        return serverId;
    }
}
