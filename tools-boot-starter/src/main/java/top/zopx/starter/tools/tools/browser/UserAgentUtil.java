package top.zopx.starter.tools.tools.browser;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;

import java.io.IOException;

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

    public static String getOs(String useragent) throws IOException {
        UASparser sparser = LOCAL.get();
        if (sparser == null)
            return "";

        return LOCAL.get().parse(useragent).getOsFamily().toLowerCase();
    }
}
