package top.zopx.goku.example.socket.biz;

import com.google.gson.JsonObject;
import io.netty.channel.ChannelHandler;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.biz.handle.BizMsgHandle;
import top.zopx.goku.example.socket.common.constant.Constant;
import top.zopx.goku.example.socket.common.util.ReadFileUtil;
import top.zopx.goku.framework.biz.ukey.UKey;
import top.zopx.goku.framework.biz.dao.MybatisDao;
import top.zopx.goku.framework.biz.redis.RedisCache;
import top.zopx.goku.framework.biz.redis.RedisPublish;
import top.zopx.goku.framework.biz.report.RedisReportServerInfo;
import top.zopx.goku.framework.biz.report.ReportServer;
import top.zopx.goku.framework.cluster.constant.ServerCommandLineEnum;
import top.zopx.goku.framework.cluster.entity.IServerInfo;
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
public class BizApp implements BaseChannelHandlerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(BizApp.class);

    private static int serverId;
    private static String serverName;
    private static String serverJobTypeSet;
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
        //CommandLnUtil.create(args, ServerCommandLineEnum.create().toArray(new CommandLnUtil.Config[0]));
        final Map<String, String> commandLnMap = ServerCommandLineEnum.createCommandLine(args);
        if (MapUtils.isEmpty(commandLnMap)) {
            LOGGER.error("缺少必要的参数信息");
            return;
        }

        serverId = StringUtil.toInteger(commandLnMap.get(ServerCommandLineEnum.SERVER_ID.getLongOpt()));
        serverName = commandLnMap.get(ServerCommandLineEnum.SERVER_NAME.getLongOpt());
        serverJobTypeSet = commandLnMap.get(ServerCommandLineEnum.SERVER_JOB_TYPE.getLongOpt());
        serverIp = commandLnMap.get(ServerCommandLineEnum.SERVER_HOST.getLongOpt());
        serverPort = StringUtil.toInteger(commandLnMap.get(ServerCommandLineEnum.SERVER_PORT.getLongOpt()));

        // 读取配置文件
        JsonObject configObj = ReadFileUtil.read(commandLnMap.get(ServerCommandLineEnum.CONFIG.getLongOpt()));
        if (null == configObj) {
            LOGGER.error("必要配置项缺失");
            return;
        }

        final RedisCache.Config redisConf = RedisCache.Config.fromJsonData(configObj);
        if (null != redisConf) {
            RedisCache.init(redisConf);
        }

        final MybatisDao.Config mybatisConf = MybatisDao.Config.fromJsonData(configObj);
        if (null != mybatisConf) {
            MybatisDao.init(mybatisConf, BizApp.class);
        }

        final UKey.Config config = UKey.Config.fromJsonData(configObj);
        if (null != config) {
            UKey.init(config);
        }

        // 启动服务
        startBizServerApp();
        // 定时上报自身信息
        startReportCurrServer();
    }

    private static void startReportCurrServer() {

        RedisPublish redisPublish = new RedisPublish();

        LOGGER.info("开始上报信息");
        ReportServer.Config config =
                new ReportServer.Config()
                        .setServerInfo(() -> {
                            final IServerInfo.ServerInfo serverInfo = new IServerInfo.ServerInfo();
                            serverInfo.setServerId(serverId);
                            serverInfo.setServerName(serverName);
                            serverInfo.setServerIp(serverIp);
                            serverInfo.setServerPort(serverPort);
                            serverInfo.setServerJobTypeSet(serverJobTypeSet);

                            serverInfo.setLoadCount(0);
                            return serverInfo;
                        })
                        .setReportServerInfo(new RedisReportServerInfo(redisPublish));
        new ReportServer(config).start();
    }

    private static void startBizServerApp() {
        new NettyServerAcceptor(
                ServerAcceptor.create()
                        .setBossThreadPool(2)
                        .setWorkThreadPool(8)
                        .setFactory(new BizApp())
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

        LOGGER.info("--> 业务服务:{} 已启动， 处理业务：{} <--",
                serverName,
                serverJobTypeSet
        );
    }

    @Override
    public ChannelHandler createWebsocketMsgHandler() {
        return new BizMsgHandle();
    }


}
