package top.zopx.testGoku.socket;

import io.netty.channel.ChannelHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.netty.bind.entity.ServerAcceptor;
import top.zopx.goku.framework.netty.bind.factory.BaseChannelHandlerFactory;
import top.zopx.goku.framework.netty.server.NettyServerAcceptor;

@Component
public class Runner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        new NettyServerAcceptor(
                ServerAcceptor.create()
                        .setFactory(new BaseChannelHandlerFactory() {
                            @Override
                            public ChannelHandler createWebsocketMsgHandler() {
                                return null;
                            }
                        })
                        .build()
        ).start();
    }
}
