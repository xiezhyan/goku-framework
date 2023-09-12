package top.zopx.goku.framework.socket.tools.circuit.chain;

import org.apache.commons.collections4.MapUtils;
import top.zopx.goku.framework.socket.core.constant.enums.CommandLineEnum;
import top.zopx.goku.framework.socket.tools.circuit.Context;
import top.zopx.goku.framework.socket.tools.reader.IReadStrategy;

import java.util.Map;

/**
 * 从配置文件中读取到第三方配置信息
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/19 18:19
 */
public class ReadConfRequestHandler implements RequestHandler {

    private final IReadStrategy readStrategy;

    public ReadConfRequestHandler(IReadStrategy readStrategy) {
        this.readStrategy = readStrategy;
    }

    @Override
    public void handleRequest(Context context) {
        if (Boolean.FALSE.equals(context.hasKey(CommandLineEnum.SERVER_CONF.getLongOpt()))) {
            LOG.error("命令行conf未被成功解析");
            return;
        }

        String conf = context.attr(CommandLineEnum.SERVER_CONF.getLongOpt()).toString();

        Map<String, Object> map = readStrategy.read(conf);

        if (MapUtils.isEmpty(map)) {
            return;
        }

        map.forEach(context::attr);
    }
}
