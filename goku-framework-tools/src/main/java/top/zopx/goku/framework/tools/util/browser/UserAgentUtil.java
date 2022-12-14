package top.zopx.goku.framework.tools.util.browser;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import org.apache.commons.lang3.StringUtils;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.tools.util.string.StringUtil;

import java.io.IOException;
import java.util.Objects;

/**
 * 对提交的UserAgent进行操作， 获取系统
 *
 * @author 俗世游子
 * @date 2020/1/26
 */
public class UserAgentUtil {

    private static UASparser UA_SPARSER = null;
    static {
        try {
            UA_SPARSER = getUaSparser();
        } catch (IOException e) {
            throw new BusException(e.getMessage());
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
        UserAgentInfo result = null;
        if (StringUtils.isBlank(userAgent)) {
            return result;
        }

        if (Objects.isNull(UA_SPARSER)) {
            try {
                UA_SPARSER = getUaSparser();
            } catch (IOException e) {
                throw new BusException(e.getMessage());
            }
        }

        try {
            cz.mallat.uasparser.UserAgentInfo info = UA_SPARSER.parse(userAgent);
            result = new UserAgentInfo();
            result.setBrowserName(info.getUaFamily());
            result.setBrowserVersion(info.getBrowserVersionInfo());
            result.setOsName(info.getOsFamily());
            result.setOsVersion(info.getOsName());
        } catch (Exception e) {
            throw new BusException(e.getMessage());
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
