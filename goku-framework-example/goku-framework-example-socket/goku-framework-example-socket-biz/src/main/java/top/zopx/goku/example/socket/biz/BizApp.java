package top.zopx.goku.example.socket.biz;

import io.netty.channel.ChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.biz.handle.BizMsgHandleMsg;
import top.zopx.goku.example.socket.biz.sub.UserLogoutSub;
import top.zopx.goku.framework.socket.core.config.constant.ServerCtlEnum;
import top.zopx.goku.framework.socket.core.config.properties.Bootstrap;
import top.zopx.goku.framework.socket.core.entity.IServerInfo;
import top.zopx.goku.framework.socket.core.pubsub.IPublish;
import top.zopx.goku.framework.socket.core.pubsub.ISubscribe;
import top.zopx.goku.framework.socket.core.pubsub.ReportServer;
import top.zopx.goku.framework.socket.core.ukey.UKey;
import top.zopx.goku.framework.socket.core.util.ReadFileUtil;
import top.zopx.goku.framework.socket.datasource.MybatisDao;
import top.zopx.goku.framework.socket.netty.handle.IChannelHandle;
import top.zopx.goku.framework.socket.netty.properties.Server;
import top.zopx.goku.framework.socket.netty.properties.Websocket;
import top.zopx.goku.framework.socket.netty.runner.ServerRunner;
import top.zopx.goku.framework.socket.redis.Redis;
import top.zopx.goku.framework.socket.redis.constant.RedisKeyEnum;
import top.zopx.goku.framework.socket.redis.pubsub.RedisPub;
import top.zopx.goku.framework.socket.redis.pubsub.RedisReport;
import top.zopx.goku.framework.socket.redis.pubsub.RedisSub;

import java.time.Duration;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public class BizApp implements IChannelHandle {

    private static final Logger LOGGER = LoggerFactory.getLogger(BizApp.class);

    private static int serverId;
    private static String serverName;
    private static String serverJobTypeSet;
    private static String serverIp;
    private static int serverPort;
    private static String serverPath;

    public static void main(String[] args) {


        /**
         * --server_id=2002
         * --server_name=biz_server
         * --server_job_type_set=AUTH,CHAT
         * --server_host=127.0.0.1
         * --server_port=54322
         * --server_path=/websocket
         * --conf=D:\working\ggg\002\goku-framework\goku-framework-example\goku-framework-example-socket\goku-framework-example-socket-biz\src\main\resources\application.json
         */
        ServerCtlEnum.parse(args);

        serverId = Integer.parseInt(ServerCtlEnum.SERVER_ID.get());

        String config = ServerCtlEnum.SERVER_CONFIG.get();
        Bootstrap configurator = ReadFileUtil.read(
                config,
                Bootstrap.class
        );

        serverJobTypeSet = ServerCtlEnum.SERVER_JOB_TYPE.get();
        serverName = ServerCtlEnum.SERVER_NAME.get();
        serverIp = ServerCtlEnum.SERVER_HOST.get();
        serverPort = Integer.parseInt(ServerCtlEnum.SERVER_PORT.get());
        serverPath = ServerCtlEnum.SERVER_PATH.get();

        Redis.init(configurator.getRedis());
        UKey.init(configurator.getUkey());
        MybatisDao.init(configurator.getDatasource(), BizApp.class);

        // 启动服务
        startBizServerApp();
        // 定时上报自身信息
        startReportCurrServer();
        // 开始订阅消息
        startSubServer();
    }

    private static void startReportCurrServer() {

        IPublish redisPublish = new RedisPub();

        LOGGER.info("开始上报信息");
        ReportServer.Config config =
                new ReportServer.Config()
                        .setServerInfo(() -> {
                            final IServerInfo.ServerInfo serverInfo = new IServerInfo.ServerInfo();
                            serverInfo.setServerId(serverId);
                            serverInfo.setServerName(serverName);
                            serverInfo.setServerIp(serverIp);
                            serverInfo.setServerPort(serverPort);
                            serverInfo.setPath(serverPath);
                            serverInfo.setServerJobTypeSet(serverJobTypeSet);

                            serverInfo.setLoadCount(0);
                            return serverInfo;
                        })
                        .setReportServerInfo(new RedisReport(redisPublish));
        new ReportServer(config).start();
    }

    private static void startBizServerApp() {
        new ServerRunner(
                Server.create()
                        .setBossThreadPool(2)
                        .setWorkThreadPool(8)
                        .setChannelHandle(new BizApp())
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

        LOGGER.info("--> 业务服务:{} 已启动， 处理业务：{} <--",
                serverName,
                serverJobTypeSet
        );
    }

    private static void startSubServer() {
        String[] channelArr = {
                RedisKeyEnum.USER_LOGOUT_NOTICE.getKey()
        };

        ISubscribe.SubscribeGroup group = new ISubscribe.SubscribeGroup();
        group.add(new UserLogoutSub());

        new RedisSub().subscribe(channelArr, group);
    }

    @Override
    public ChannelHandler createWebsocketMsgHandler() {
        return new BizMsgHandleMsg();
    }
}
