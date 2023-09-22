package top.zopx.goku.framework.socket.core.util;

import java.net.InetAddress;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/25 20:18
 */
public class IP {

    private static final String LOCAL = "127.0.0.1";

    public static String getLocalIP() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            if (null == address) {
                return LOCAL;
            }

            return address.getHostAddress();
        } catch (Exception e) {
            return LOCAL;
        }
    }
}
