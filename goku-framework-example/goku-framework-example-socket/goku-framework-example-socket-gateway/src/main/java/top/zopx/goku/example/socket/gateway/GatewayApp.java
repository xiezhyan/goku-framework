package top.zopx.goku.example.socket.gateway;

import io.netty.channel.ChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.gateway.handle.ClientInnerMsgHandleMsg;
import top.zopx.goku.example.socket.gateway.handle.ClientMsgHandleMsg;
import top.zopx.goku.example.socket.gateway.sub.ConnectionTransferSub;
import top.zopx.goku.example.socket.gateway.sub.DisconnectDuplicateLoginSub;
import top.zopx.goku.example.socket.gateway.sub.KickOutUserSub;
import top.zopx.goku.framework.socket.core.config.constant.ServerCtlEnum;
import top.zopx.goku.framework.socket.core.config.properties.Bootstrap;
import top.zopx.goku.framework.socket.core.pubsub.ISubscribe;
import top.zopx.goku.framework.socket.core.ukey.UKey;
import top.zopx.goku.framework.socket.core.util.ReadFileUtil;
import top.zopx.goku.framework.socket.netty.handle.IChannelHandle;
import top.zopx.goku.framework.socket.netty.properties.Server;
import top.zopx.goku.framework.socket.netty.properties.Websocket;
import top.zopx.goku.framework.socket.netty.runner.ServerRunner;
import top.zopx.goku.framework.socket.redis.Redis;
import top.zopx.goku.framework.socket.redis.constant.RedisKeyEnum;
import top.zopx.goku.framework.socket.redis.pubsub.RedisSub;
import top.zopx.goku.framework.socket.redis.selector.ServerConnectSub;

import java.time.Duration;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public class GatewayApp implements IChannelHandle {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayApp.class);

    private static int serverId;
    private static String serverName;
    private static String serverIp;
    private static int serverPort;
    private static String serverPath;

    public static void main(String[] args) {

        /**
         * --server_id=1001
         * --server_name=gateway_server
         * --server_job_type_set=GATEWAY
         * --server_host=127.0.0.1
         * --server_port=12345
         * --server_path=/websocket
         * --conf=D:\working\ggg\002\goku-framework\goku-framework-example\goku-framework-example-socket\goku-framework-example-socket-gateway\src\main\resources\application.json
         */
        ServerCtlEnum.parse(args);

        serverId = Integer.parseInt(ServerCtlEnum.SERVER_ID.get());

        String config = ServerCtlEnum.SERVER_CONFIG.get();
        Bootstrap configurator = ReadFileUtil.read(
                config,
                Bootstrap.class
        );

        serverName = ServerCtlEnum.SERVER_NAME.get();
        serverIp = ServerCtlEnum.SERVER_HOST.get();
        serverPort = Integer.parseInt(ServerCtlEnum.SERVER_PORT.get());
        serverPath = ServerCtlEnum.SERVER_PATH.get();

        Redis.init(configurator.getRedis());
        UKey.init(configurator.getUkey());

        startGatewayServerApp();
        startSubServer();
    }

    private static void startGatewayServerApp() {
        new ServerRunner(
                Server.create()
                        .setBossThreadPool(2)
                        .setWorkThreadPool(8)
                        .setChannelHandle(new GatewayApp())
                        .setReadTimeout(Duration.ofSeconds(45L))
                        .setWriteTimeout(Duration.ofSeconds(60L))
                        .setWebsocket(
                                Websocket.create()
                                        .setHost(serverIp)
                                        .setPort(serverPort)
                                        .setPath(serverPath)
                                        .build()
                        )
                        .build()
        ).start();
        LOGGER.info("--> 网关服务:{} 已启动 <--", serverName);
    }

    private static void startSubServer() {
        String[] channelArr = {
                RedisKeyEnum.REGISTER_SERVER.getKey(),
                RedisKeyEnum.CONNECTION_TRANSFER_NOTICE.getKey(),
                RedisKeyEnum.KICK_OUT_USER_NOTICE.getKey(),
                RedisKeyEnum.DISCONNECT_DUPLICATE_LOGIN.getKey()
        };

        ISubscribe.SubscribeGroup group = new ISubscribe.SubscribeGroup();
        group.add(
                new ServerConnectSub(
                        new IChannelHandle() {
                            @Override
                            public ChannelHandler createWebsocketMsgHandler() {
                                return new ClientInnerMsgHandleMsg();
                            }
                        },
                        serverId,
                        "gateway_id", String.valueOf(serverId)
                )
        );
        group.add(new ConnectionTransferSub());
        group.add(new KickOutUserSub());
        group.add(new DisconnectDuplicateLoginSub());

        new RedisSub().subscribe(channelArr, group);

    }


    @Override
    public ChannelHandler createWebsocketMsgHandler() {
        return new ClientMsgHandleMsg();
    }

    public static int getServerId() {
        return serverId;
    }
}
