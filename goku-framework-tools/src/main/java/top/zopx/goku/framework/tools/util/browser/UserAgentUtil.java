package top.zopx.goku.framework.tools.util.browser;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import org.apache.commons.lang3.StringUtils;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

/**
 * 对提交的UserAgent进行操作， 获取系统
 *
 * @author Mr.Xie
 * @date 2020/1/26
 */
public final class UserAgentUtil {

    private UserAgentUtil() {}

    private static UASparser uaSparser = null;
    static {
        try {
            uaSparser = getUaSparser();
        } catch (IOException e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }

    private static UASparser getUaSparser() throws IOException {
        return new UASparser(OnlineUpdater.getVendoredInputStream());
    }


    /**
     * 获取UserAgent信息
     *
     * @param userAgent userAgent
     * @return UserAgentInfo
     */
    public static UserAgentInfo analyticUserAgent(String userAgent) {
        UserAgentInfo result = new UserAgentInfo();
        if (StringUtils.isBlank(userAgent)) {
            return result;
        }

        if (Objects.isNull(uaSparser)) {
            try {
                uaSparser = getUaSparser();
            } catch (IOException e) {
                throw new BusException(e.getMessage(), IBus.ERROR_CODE);
            }
        }

        try {
            cz.mallat.uasparser.UserAgentInfo info = uaSparser.parse(userAgent);
            result.setBrowserName(info.getUaFamily());
            result.setBrowserVersion(info.getBrowserVersionInfo());
            result.setOsName(info.getOsFamily());
            result.setOsVersion(info.getOsName());
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }

        return result;
    }


    public static class UserAgentInfo implements Serializable {
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
