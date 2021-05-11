package top.zopx.starter.tools.digest.md5;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author 俗世游子
 * @date 2021/5/11
 */
public enum  Md5Util {
    INSTANCE,
    ;

    /**
     * 加密
     * @param buffer 需要加密的字符串字节数组
     * @return 加密成功的字符串
     */
    public String digest(byte[] buffer) {
        return DigestUtils.md5Hex(buffer);
    }

    /**
     * 加密
     * @param buffer 需要加密的字符串字节数组
     * @param num 加密次数
     * @return 加密成功的字符串
     */
    public String digest(byte[] buffer, int num) {
        String result = "";
        for (int i = 0; i < num; i++) {
            result = digest(buffer);
        }
        return result;
    }
}
