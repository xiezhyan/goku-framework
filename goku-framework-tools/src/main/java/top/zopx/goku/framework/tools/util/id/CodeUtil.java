package top.zopx.goku.framework.tools.util.id;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/6/7 20:56
 */
public final class CodeUtil {

    private CodeUtil() {}

    private static final String DEFAULT = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";

    /**
     * 随机number字符串
     *
     * @param count 位数
     * @return String
     */
    public static String genericNumCode(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    /**
     * 自定义Code
     *
     * @param chars chars
     * @param count 位数
     * @return String
     */
    public static String genericOpeCode(String chars, int count) {
        if (StringUtils.isBlank(chars)) {
            return genericOpeCode(
                    DEFAULT,
                    count
            );
        }
        return RandomStringUtils.random(count, chars);
    }

    /**
     * 随机大小写
     *
     * @return String
     */
    public static String genericAlphCode(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }

}
