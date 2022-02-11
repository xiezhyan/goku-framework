package top.zopx.starter.tools.tools.json;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.starter.tools.basic.R;

/**
 * 默认Json方法
 *
 * @author xiezhongyan
 * @email xiezhyan@126.com
 * @date 2022/2/11
 */
public final class JsonUtilTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtilTest.class);

    @Test
    public void toJsonStr() {

        R<Boolean> r = R.result(true);

        LOGGER.info("r json = {}", JsonUtil.getInstance().getJson().toJson(r));

    }

    @Test
    public void toJson() {
        String json = "{\"meta\":{\"success\":true,\"message\":\"OK\",\"code\":200},\"data\":true}";
        LOGGER.info("r json = {}", JsonUtil.getInstance().getJson().toObject(json, R.class));
    }
}
