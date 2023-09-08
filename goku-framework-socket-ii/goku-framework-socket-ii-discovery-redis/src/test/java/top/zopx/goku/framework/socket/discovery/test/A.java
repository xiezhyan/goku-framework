package top.zopx.goku.framework.socket.discovery.test;

import io.netty.bootstrap.ServerBootstrap;
import top.zopx.goku.framework.socket.core.server.Server;
import top.zopx.goku.framework.socket.core.server.ServerRunner;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/9/8 08:19
 */
public class A extends ServerRunner {

    public A(Server server) {
        super(server);
    }

    @Override
    protected void buildServerOption(ServerBootstrap serverBootstrap) {

    }
}
