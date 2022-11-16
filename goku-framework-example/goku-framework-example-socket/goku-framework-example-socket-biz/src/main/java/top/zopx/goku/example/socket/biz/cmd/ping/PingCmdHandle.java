package top.zopx.goku.example.socket.biz.cmd.ping;

import com.google.gson.JsonObject;
import io.netty.channel.ChannelHandler;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.biz.handle.BizMsgHandle;
import top.zopx.goku.example.socket.common.constant.Constant;
import top.zopx.goku.example.socket.common.util.ReadFileUtil;
import top.zopx.goku.example.socket.common.util.UKey;
import top.zopx.goku.example.socket.proto.common.Common;
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
import top.zopx.goku.framework.netty.bind.handler.BaseCmdHandleContext;
import top.zopx.goku.framework.netty.bind.handler.ICmdContextHandler;
import top.zopx.goku.framework.netty.server.NettyServerAcceptor;
import top.zopx.goku.framework.tools.util.string.StringUtil;

import java.time.Duration;
import java.util.Map;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public class PingCmdHandle implements ICmdContextHandler<BaseCmdHandleContext, Common.PingRequest> {
    @Override
    public void cmd(BaseCmdHandleContext ctx, Common.PingRequest cmd) {
        if (null == ctx ||
                null == cmd) {
            return;
        }

        LOGGER.debug(
                "收到内部服务器消息, gatewayId = {}, remoteSessionId = {}, fromUserId = {},  msgData = {}",
                ctx.getGatewayServerId(),
                ctx.getRemoteSessionId(),
                ctx.getFromUserId(),
                cmd.getPingId()
        );

        ctx.writeAndFlush(
                Common.PingResponse.newBuilder().setPingId(cmd.getPingId()).build()
        );
    }
}
