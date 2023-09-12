package top.zopx.goku.framework.socket.discovery.handle;

import top.zopx.goku.framework.socket.tools.circuit.Context;
import top.zopx.goku.framework.socket.tools.circuit.chain.RequestHandler;
import top.zopx.goku.framework.socket.core.pubsub.ISubscribe;
import top.zopx.goku.framework.socket.discovery.pubsub.RedisSub;

/**
 * 基于Redis订阅服务
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 13:40
 */
public class RedisSubRequestHandler implements RequestHandler {

    private final String[] chArray;
    private final ISubscribe h;

    public RedisSubRequestHandler(String[] chArray, ISubscribe h) {
        this.chArray = chArray;
        this.h = h;
    }

    @Override
    public void handleRequest(Context context) {
        RedisSub.getInstance()
                .subscribe(
                        chArray, h
                );
    }
}
