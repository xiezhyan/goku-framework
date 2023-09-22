package top.zopx.goku.framework.socket.core.circuit.chain;

import org.apache.commons.lang3.ArrayUtils;
import top.zopx.goku.framework.socket.core.circuit.Context;
import top.zopx.goku.framework.socket.core.constant.enums.CommandLineEnum;

/**
 * 解析命令行参数
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/19 17:48
 */
public class ParseRequestHandler implements RequestHandler {

    private final String[] args;

    public ParseRequestHandler(String[] args) {
        this.args = args;
    }

    @Override
    public void handleRequest(Context context) {
        if (ArrayUtils.isEmpty(args)) {
            return;
        }
        CommandLineEnum.parse(context, args);
        LOG.info(
                "。。。解析命令行参数完成，获取测试数据：{}。。。",
                context.attr(CommandLineEnum.SERVER_PATH.getLongOpt())
        );
    }
}
