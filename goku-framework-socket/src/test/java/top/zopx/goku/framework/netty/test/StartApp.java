package top.zopx.goku.framework.netty.test;

import top.zopx.goku.framework.netty.bind.entity.ServerAcceptor;
import top.zopx.goku.framework.netty.server.NettyServerAcceptor;

/**
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/7/29
 */
public class StartApp {
    public static void main(String[] args) {

        new NettyServerAcceptor(
                ServerAcceptor.create()
                        .setFactory(new ChannelHandlerFactory())
                        .build()
        ).start();

    }
}
