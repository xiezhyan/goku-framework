package top.zopx.starter.tools.tools.strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则验证
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public class MatcherUtil {
    public static final String EMAIL_REGEX = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
    public static final String URL_REGEX = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    public static final String PHONE_REGEX = "^(\\d{3,4}-)?\\d{6,8}$";
    public static final String POSTAL_CODE_REGEX = "^\\d{6}$";
    public static final String TEL_REGEX = "^((13[0-9])|(14[5-9])|(15([0-3]|[5-9]))|(16[6-7])|(17[1-8])|(18[0-9])|(19[1|3])|(19[5|6])|(19[8|9]))\\d{8}$";
    public static final String NUMBER_REGEX = "^-?[0-9]\\d*$";
    public static final String FLOAT_REGEX = "^-?[0-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$";
    public static final String LETTER_REGEX = "^[A-Za-z]+$";
    public static final String CHINESE_REGEX = "^[\u4e00-\u9fa5],{0,}$";
    private static final String IP_NUM = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    public static final String IP_REGEX = "^" + IP_NUM + "\\." + IP_NUM + "\\." + IP_NUM + "\\." + IP_NUM + "$";

    /**
     * 验证邮箱
     *
     * @return true || false
     */
    public static boolean isEmail(String str) {
        return match(EMAIL_REGEX, str);
    }

    /**
     * 验证IP地址
     *
     * @return true || false
     */
    public static boolean isIp(String str) {
        return match(IP_REGEX, str);
    }

    /**
     * 验证网址Url
     *
     * @return true || false
     */
    public static boolean isUrl(String str) {
        return match(URL_REGEX, str);
    }

    /**
     * 验证电话号码
     *
     * @return true || false
     */
    public static boolean isPhone(String str) {
        return match(PHONE_REGEX, str);
    }

    /**
     * 验证输入邮政编号
     *
     * @return true || false
     */
    public static boolean isPostalCode(String str) {
        return match(POSTAL_CODE_REGEX, str);
    }

    /**
     * 验证输入手机号码
     *
     * @return true || false
     */
    public static boolean isTelephone(String str) {
        return match(TEL_REGEX, str);
    }

    /**
     * 替换特殊字符
     *
     * @param str 内容
     * @return 替换后的内容
     */
    public static String replaceMark(String str) {
        String reg = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 验证是否为整数
     *
     * @return true || false
     */
    public static boolean isNumber(String str) {
        return match(NUMBER_REGEX, str);
    }

    /**
     * 验证是否为浮点数
     *
     * @return true || false
     */
    public static boolean isFloat(String str) {
        return match(FLOAT_REGEX, str);
    }

    /**
     * 验证输入字母
     *
     * @return true || false
     */
    public static boolean isLetter(String str) {
        return match(LETTER_REGEX, str);
    }

    /**
     * 验证验证输入汉字
     *
     * @return true || false
     */
    public static boolean isChinese(String str) {
        return match(CHINESE_REGEX, str);
    }

    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    public static boolean match(String regex, String str) {
        return Pattern.matches(regex, str);
    }
}
