package top.zopx.starter.tools.tools.strings;

import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;

/**
 * version: 加密
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public class DigestUtil {

    private DigestUtil() {
    }

    private static volatile DigestUtil instance;

    public static DigestUtil getInstance() {
        if (instance == null) {
            synchronized (DigestUtil.class) {
                if (instance == null) {
                    instance = new DigestUtil();
                }
            }
        }
        return instance;
    }

    /**
     * MD5加密
     */
    public String md5(String msg) {
        return DigestUtils.md5Hex(msg);
    }

    /**
     * base64加密
     */
    public String base64Encode(String msg) {
        return Base64Utils.encodeToString(msg.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * base64解密
     */
    @SneakyThrows
    public String base64Decode(String msg) {
        return new String(Base64Utils.decodeFromString(msg));
    }
}
