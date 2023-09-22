package top.zopx.goku.framework.socket.core.circuit.chain;

import top.zopx.goku.framework.socket.core.circuit.Context;
import top.zopx.goku.framework.socket.core.constant.enums.CommandLineEnum;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;

/**
 * 从配置文件中读取到第三方配置信息
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/19 18:19
 */
public class ReadConfRequestHandler implements RequestHandler {

    @Override
    public void handleRequest(Context context) {
        if (Boolean.FALSE.equals(context.hasKey(CommandLineEnum.SERVER_CONF.getLongOpt()))) {
            LOG.error("命令行conf未被成功解析");
            return;
        }

        String conf = context.attr(CommandLineEnum.SERVER_CONF.getLongOpt()).toString();

        try (BufferedReader br = new BufferedReader(new FileReader(conf))) {
            String text = br.lines().collect(Collectors.joining());

            GsonUtil.getInstance().toMap(text)
                    .forEach((k, v) -> context.attr(k.toString(), v));
            LOG.info("。。。配置文件数据读取成功，已加入到Request中。。。");
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }
}
