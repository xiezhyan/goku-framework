package top.zopx.goku.framework.socket.tools.circuit.chain;

import top.zopx.goku.framework.socket.tools.circuit.Context;
import top.zopx.goku.framework.socket.core.constant.enums.CommandLineEnum;
import top.zopx.goku.framework.socket.core.server.Server;
import top.zopx.goku.framework.socket.core.server.ServerRunner;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.util.function.Supplier;

/**
 * 启动netty服务
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/19 18:31
 */
public class StartServerRequestHandler implements RequestHandler {
    private final Server server;

    public StartServerRequestHandler(Supplier<Server> supplier) {
        this.server = supplier.get();
    }

    @Override
    public void handleRequest(Context context) {
        if (Boolean.FALSE.equals(
                context.hasKey(
                        CommandLineEnum.SERVER_HOST.getLongOpt(),
                        CommandLineEnum.SERVER_PORT.getLongOpt()))
        ) {
            throw new BusException("SERVER_HOST, SERVER_PORT 尚未准备", IBus.ERROR_CODE);
        }

        new ServerRunner(server)
                .start();
        LOG.info(
                "。。。服务 {} 启动成功， 处理业务：{}。。。",
                context.attr(CommandLineEnum.SERVER_ID.getLongOpt()),
                context.attr(CommandLineEnum.SERVER_JOB_TYPE.getLongOpt())
        );
    }
}
