package top.zopx.goku.framework.socket.core;

import io.netty.channel.ChannelHandler;
import org.junit.Test;
import top.zopx.goku.framework.socket.core.circuit.Context;
import top.zopx.goku.framework.socket.core.circuit.chain.*;
import top.zopx.goku.framework.socket.core.cmd.IChannelHandle;
import top.zopx.goku.framework.socket.core.entity.ReportServerInfo;
import top.zopx.goku.framework.socket.core.pubsub.IReport;
import top.zopx.goku.framework.socket.core.server.Server;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/19 16:55
 */
public class ServerRunTest {

    @Test
    public void test() {
        // nothing
    }

    public static void main(String[] args) {
        Context context = new Context(args);
        context.add(new ReadConfRequestHandler());
        context.add(
                new StartServerRequestHandler(
                        () -> Server.create()
                                .setChannelHandle(new IChannelHandle() {
                                    @Override
                                    public ChannelHandler createWebsocketMsgHandler() {
                                        return IChannelHandle.super.createWebsocketMsgHandler();
                                    }
                                })
                                .build()
                )
        );
        context.add(
                new ReportServerInfoRequestHandler(new IReport() {
                    @Override
                    public void report(ReportServerInfo serverInfo) {
                        LOGGER.debug("serverInfo = {}", serverInfo.getServerId());
                    }
                })
        );

        context.execute();
    }
}
