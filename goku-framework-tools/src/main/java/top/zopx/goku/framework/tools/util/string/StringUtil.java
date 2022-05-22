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
     * version:得到UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }

    public static int getHash(String str) {
        int hash = -2128831035;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * 16777619;
        }
        hash += (hash << 13);
        hash ^= hash >> 7;
        hash += (hash << 3);
        hash ^= hash >> 17;
        hash += (hash << 5);
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }
}
