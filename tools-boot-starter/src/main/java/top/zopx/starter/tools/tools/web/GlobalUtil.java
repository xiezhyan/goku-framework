package top.zopx.starter.tools.tools.web;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 获取用户访问IP等
 * @author sanq.Yan
 * @date 2020/1/26
 */
public class GlobalUtil {

    public static String getBrowserByKey(HttpServletRequest request, String key) {
        return request.getHeader(key);
    }

    public static String getBrowserHost(HttpServletRequest request) {
        return getBrowserByKey(request, HttpHeaders.HOST);
    }

    public static String getBrowserRefer(HttpServletRequest request) {
        return getBrowserByKey(request, HttpHeaders.REFERER);
    }

    public static String getBrowserAgent(HttpServletRequest request) {
        return getBrowserByKey(request, HttpHeaders.USER_AGENT);
    }

    public static String getBrowserIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
