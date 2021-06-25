package top.zopx.starter.tools.tools.browser;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;
import top.zopx.starter.tools.tools.strings.StringUtil;
import top.zopx.starter.tools.tools.web.LogUtil;

import java.io.IOException;

/**
 * 对提交的UserAgent进行操作， 获取系统
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
     * 获取UserAgent信息
     *
     * @param userAgent userAgent
     * @return UserAgentInfo
     */
    public static UserAgentInfo analyticUserAgent(String userAgent) {
        UserAgentInfo result = null;
        if (StringUtil.isBlank(userAgent)) {
            return result;
        }

        try {
            cz.mallat.uasparser.UserAgentInfo info = LOCAL.get().parse(userAgent);
            result = new UserAgentInfo();
            result.setBrowserName(info.getUaFamily());
            result.setBrowserVersion(info.getBrowserVersionInfo());
            result.setOsName(info.getOsFamily());
            result.setOsVersion(info.getOsName());
        } catch (Exception e) {
            LogUtil.getInstance(UserAgentUtil.class).error("解析UserAgent出错：【{}】", e.getMessage());
        }

        return result;
    }


    public static class UserAgentInfo {
        private String browserName; // 浏览器名称
        private String browserVersion; // 浏览器版本号
        private String osName; // 操作系统名称
        private String osVersion; // 操作系统版本号

        public String getBrowserName() {
            return browserName;
        }

        public void setBrowserName(String browserName) {
            this.browserName = browserName;
        }

        public String getBrowserVersion() {
            return browserVersion;
        }

        public void setBrowserVersion(String browserVersion) {
            this.browserVersion = browserVersion;
        }

        public String getOsName() {
            return osName;
        }

        public void setOsName(String osName) {
            this.osName = osName;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public void setOsVersion(String osVersion) {
            this.osVersion = osVersion;
        }

        @Override
        public String toString() {
            return "UserAgentInfo [browserName=" + browserName + ", browserVersion=" + browserVersion + ", osName="
                    + osName + ", osVersion=" + osVersion + "]";
        }
    }

}
