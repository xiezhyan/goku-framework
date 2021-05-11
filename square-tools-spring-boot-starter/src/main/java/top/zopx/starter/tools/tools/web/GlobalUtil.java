package top.zopx.starter.tools.tools.web;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 全局对象
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public final class GlobalUtil {
    private final static String TOKEN_KEY = "token";

    public static class Request {
        /**
         * 从RequestHeader 中获取key
         *
         * @param request request
         * @param key     key
         * @return value
         */
        public static String getBrowserByKey(HttpServletRequest request, String key) {
            return request.getHeader(key);
        }

        /**
         * 从RequestHeader中获取token
         *
         * @param request request
         * @return token value
         */
        public static String getToken(HttpServletRequest request) {
            return getBrowserByKey(request, TOKEN_KEY);
        }

        /**
         * 从RequestHeader中获取host
         *
         * @param request requests
         * @return host value
         */
        public static String getBrowserHost(HttpServletRequest request) {
            return getBrowserByKey(request, HttpHeaders.HOST);
        }

        /**
         * 从RequestHeader中获取host
         *
         * @param request requests
         * @return refer value
         */
        public static String getBrowserRefer(HttpServletRequest request) {
            return getBrowserByKey(request, HttpHeaders.REFERER);
        }

        /**
         * 从RequestHeader中获取host
         *
         * @param request requests
         * @return userAgent value
         */
        public static String getBrowserAgent(HttpServletRequest request) {
            return getBrowserByKey(request, HttpHeaders.USER_AGENT);
        }

        /**
         * 从RequestHeader中获取host
         *
         * @param request requests
         * @return ip
         */
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

        /**
         * 获取Request对象
         */
        public static HttpServletRequest getRequest() {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            return servletRequestAttributes.getRequest();
        }
    }

    public static class CurrentRequest {
        /**
         * 从RequestHeader 中获取key
         *
         * @param key     key
         * @return value
         */
        public static String getBrowserByKey(String key) {
            return getRequest().getHeader(key);
        }

        /**
         * 从RequestHeader中获取token
         *
         * @return token value
         */
        public static String getToken() {
            return getBrowserByKey(TOKEN_KEY);
        }

        /**
         * 从RequestHeader中获取host
         *
         * @return host value
         */
        public static String getBrowserHost() {
            return getBrowserByKey(HttpHeaders.HOST);
        }

        /**
         * 从RequestHeader中获取host
         *
         * @return refer value
         */
        public static String getBrowserRefer() {
            return getBrowserByKey(HttpHeaders.REFERER);
        }

        /**
         * 从RequestHeader中获取host
         *
         * @return userAgent value
         */
        public static String getBrowserAgent() {
            return getBrowserByKey(HttpHeaders.USER_AGENT);
        }

        /**
         * 从RequestHeader中获取host
         * @return ip
         */
        public static String getBrowserIp() {
            String ip = getBrowserByKey("x-forwarded-for");

            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                // 多次反向代理后会有多个ip值，第一个ip才是真实ip
                if (ip.contains(",")) {
                    ip = ip.split(",")[0];
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = getBrowserByKey("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = getBrowserByKey("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = getBrowserByKey("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = getBrowserByKey("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = getBrowserByKey("X-Real-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = getRequest().getRemoteAddr();
            }
            return ip;
        }

        /**
         * 获取Request对象
         */
        public static HttpServletRequest getRequest() {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            return servletRequestAttributes.getRequest();
        }
    }

    public static class Response {
        /**
         * 将token写入到response中
         *
         * @param response   response
         * @param tokenValue token value
         */
        public static void setToken(HttpServletResponse response, String tokenValue) {
            set(response, TOKEN_KEY, tokenValue);
        }

        /**
         * 将指定的值写入到response中
         *
         * @param response response
         * @param key      key
         * @param value    value
         */
        public static void set(HttpServletResponse response, String key, String value) {
            response.setHeader("Access-Control-Expose-Headers", key);
            response.setHeader(key, value);
        }

        /**
         * 通过Response将内容写出去
         * @param response HttpServletResponse
         * @param msg 发送内容
         * @throws IOException IOException
         */
        public static void write(HttpServletResponse response, String msg) throws IOException {
            response.setContentType("application/json;charset=utf-8");
            try (final PrintWriter writer = response.getWriter()) {
                writer.write(msg);
                writer.flush();
            }
        }

        /**
         * 获取Response对象
         */
        public static HttpServletResponse getResponse() {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            return servletRequestAttributes.getResponse();
        }
    }

    public static class CurrentResponse {
        /**
         * 将token写入到response中
         *
         * @param tokenValue token value
         */
        public static void setToken(String tokenValue) {
            set(TOKEN_KEY, tokenValue);
        }

        /**
         * 将指定的值写入到response中
         *
         * @param key      key
         * @param value    value
         */
        public static void set(String key, String value) {
            final HttpServletResponse response = getResponse();
            response.setHeader("Access-Control-Expose-Headers", key);
            response.setHeader(key, value);
        }

        /**
         * 通过Response将内容写出去
         * @param msg 发送内容
         * @throws IOException IOException
         */
        public static void write(String msg) throws IOException {
            final HttpServletResponse response = getResponse();

            response.setContentType("application/json;charset=utf-8");
            try (final PrintWriter writer = response.getWriter()) {
                writer.write(msg);
                writer.flush();
            }
        }

        /**
         * 获取Response对象
         */
        public static HttpServletResponse getResponse() {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            return servletRequestAttributes.getResponse();
        }
    }
}
