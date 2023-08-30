package top.zopx.goku.example.socket.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.proto.auth.Auth;
import top.zopx.goku.example.socket.proto.common.Common;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 异步执行
 *
 * @author Mr.Xie
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
                        .setLogin(GsonUtil.getInstance().toJson(map))
                        .build()
        ));
        // AC4AZAgCEih7InBhc3N3b3JkIjoiMTIzNDU2IiwidXNlcm5hbWUiOiJhZG1pbiJ9
    }
}
