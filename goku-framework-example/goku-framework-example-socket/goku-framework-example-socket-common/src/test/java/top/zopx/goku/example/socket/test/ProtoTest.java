package top.zopx.goku.example.socket.test;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.proto.auth.Auth;
import top.zopx.goku.example.socket.proto.common.Common;
import top.zopx.goku.framework.biz.recognizer.BaseCmdHandlerFactory;
import top.zopx.goku.framework.netty.bind.handler.BaseCmdHandleContext;
import top.zopx.goku.framework.netty.execute.MainThreadPoolExecutor;
import top.zopx.goku.framework.tools.digest.base64.Base64Util;
import top.zopx.goku.framework.tools.util.json.JsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 异步执行
 *
 * @author 俗世游子
 * @date 2022/2/4
 * @email xiezhyan@126.com
 */
public class ProtoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtoTest.class);

    @Test
    public void pingTest() {
        LOGGER.info("encode = {}", ByteUtil.getByteData(Common.PingRequest.newBuilder().setPingId(1).build()));
    }

    @Test
    public void loginTest() {
        Map<String, String> map = new HashMap<>(4);
        map.put("username", "admin");
        map.put("password", "123456");

        LOGGER.info("encode = {}", ByteUtil.getByteData(
                Auth.LoginRequest.newBuilder()
                        .setLoginType(1)
                        .setLogin(JsonUtil.getInstance().toJson(map))
                        .build()
        ));
    }
}
