package top.zopx.goku.framework.socket.discovery.test;

import io.netty.channel.ChannelHandler;
import org.junit.Test;
import top.zopx.goku.framework.socket.core.circuit.Context;
import top.zopx.goku.framework.socket.core.circuit.chain.ReadConfRequestHandler;
import top.zopx.goku.framework.socket.core.circuit.chain.RequestHandler;
import top.zopx.goku.framework.socket.core.circuit.chain.StartServerRequestHandler;
import top.zopx.goku.framework.socket.core.cmd.IChannelHandle;
import top.zopx.goku.framework.socket.core.server.Server;
import top.zopx.goku.framework.socket.core.server.Websocket;
import top.zopx.goku.framework.socket.discovery.constant.RedisKeyEnum;
import top.zopx.goku.framework.socket.discovery.handle.RedisConfigureInitRequestHandler;
import top.zopx.goku.framework.socket.discovery.handle.RedisReportServerInfoRequestHandler;
import top.zopx.goku.framework.socket.discovery.handle.RedisSubRequestHandler;
import top.zopx.goku.framework.socket.discovery.pubsub.ServerConnectSub;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 16:13
 */
public class RunnerTest {

    @Test
    public void test() {
        // nothing
    }

    public static void main(String[] args) {
        Context context = new Context(args);

        context.add(new ReadConfRequestHandler());
        context.add(new RedisConfigureInitRequestHandler());
        context.add(
                new StartServerRequestHandler(
                        () -> Server.create()
                                .setWebsocket(
                                        Websocket.create()
                                                .setPort(Context.getServerPort())
                                                .setPath(Context.getServerPath())
                                                .build()
                                )
                                .setChannelHandle(new IChannelHandle() {
                                    @Override
                                    public ChannelHandler createWebsocketMsgHandler() {
                                        return IChannelHandle.super.createWebsocketMsgHandler();
                                    }
                                })
                                .build()
                )
        );
        context.add(new RedisReportServerInfoRequestHandler());
        context.add(new RedisSubRequestHandler(
                new String[]{RedisKeyEnum.REGISTER_SERVER.getKey()},
                new ServerConnectSub(new IChannelHandle() {
                    @Override
                    public ChannelHandler createWebsocketMsgHandler() {
                        return IChannelHandle.super.createWebsocketMsgHandler();
                    }
                }, "gateway_id", Context.getServerId() + "")
        ));
        context.add(new RequestHandler() {
            @Override
            public void handleRequest(Context context) {
                // 执行相关操作。。。
            }
        });

        context.execute();
    }

}
