package top.zopx.goku.framework.tools.pass.codec.base64;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

/**
 * @author Mr.Xie
 * @date 2021/5/11
 */
public enum Base64Util {

    /**
     * 单例
     */
    INSTANCE,
    ;

    /**
     * 转码
     *
     * @param buffer 字节数组
     * @return 转码字符串
     */
    public String encode(byte[] buffer) {
        return Base64.encodeBase64String(buffer);
    }

    /**
     * 转码
     *
     * @param buffer String
     * @return String
     */
    public String encode(String buffer) {
        return encode(buffer.getBytes());
    }

    /**
     * 解码
     *
     * @param buffer 字节数组
     * @return 解码字符串
     */
    public String decode(byte[] buffer) {
        return new String(Base64.decodeBase64(buffer), StandardCharsets.UTF_8);
    }

    /**
     * 解码
     *
     * @param buffer 字节数组
     * @return byte[]
     */
    public byte[] decode(String buffer) {
        return Base64.decodeBase64(buffer);
    }
}
