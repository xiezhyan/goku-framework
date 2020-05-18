package top.zopx.starter.tools.tools.browser;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Objects;

/**
 * version: 对提交的UserAgent进行操作， 获取系统
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public class UserAgentUtil {

    public static final ThreadLocal<UASparser> LOCAL = ThreadLocal.withInitial(() -> {
        try {
            return new UASparser(OnlineUpdater.getVendoredInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    });

    /**
     * 得到 UserAgentInfo
     */
    @SneakyThrows
    public static UserAgentInfo getUserAgentInfo(String userAgent) {
        UASparser sparser = LOCAL.get();
        if (sparser == null)
            return null;

        return sparser.parse(userAgent);
    }

    /**
     * 得到设备
     *  android， ios， window
     */
    public static String getOs(String userAgent) {
        UserAgentInfo userAgentInfo = getUserAgentInfo(userAgent);
        if (null == userAgentInfo)
            return "";

        return userAgentInfo.getOsFamily().toLowerCase();
    }
}
