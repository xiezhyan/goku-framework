package top.zopx.goku.framework.tools.util.string;


import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.UUID;

/**
 * 常用字符串操作
 *
 * @author 俗世游子
 * @date 2020/1/26
 */
public class StringUtil extends StringUtils {

    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf(val.toString().trim());
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    /**
     * 字符串间比较
     *
     * @param s 字符串1
     * @param t 字符串2
     * @return 是否相等
     */
    public static boolean equals(String s, String t) {
        return Objects.equals(s, t);
    }

    /**
     * 得到UUID
     */
    @SuppressWarnings("all")
    public static String genericUUID() {
        return UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }
}
