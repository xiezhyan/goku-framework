package top.zopx.goku.framework.socket.tools.circuit.chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.socket.tools.circuit.Context;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/19 17:47
 */
public interface RequestHandler {

    Logger LOG = LoggerFactory.getLogger(RequestHandler.class);

    void handleRequest(Context context);

}
