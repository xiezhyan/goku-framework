package top.zopx.starter.tools.tools.strings;

import lombok.SneakyThrows;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

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

    public String md5(String msg) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] bs = digest.digest(msg.getBytes(StandardCharsets.UTF_8));
        if (bs != null) {
            StringBuffer sb = new StringBuffer();
            String hexStr;
            for (byte b : bs) {
                int i = b & 0xFF;
                hexStr = Integer.toHexString(i);
                if (hexStr.length() < 2) {
                    hexStr = "0" + hexStr;
                }
                sb.append(hexStr);
            }
            return sb.toString();
        }
        return "";
    }

    /**
     * base64加密
     */
    public String base64Encode(String msg){
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(msg.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * base64解密
     */
    @SneakyThrows
    public String base64Decode(String msg) {
        BASE64Decoder decoder = new BASE64Decoder();
        return new String(decoder.decodeBuffer(msg), StandardCharsets.UTF_8);
    }
}
