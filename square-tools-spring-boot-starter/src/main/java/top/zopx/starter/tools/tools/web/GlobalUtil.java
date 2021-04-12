package top.zopx.starter.tools.tools.web;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 获取用户访问IP等
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public class GlobalUtil {
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
         * @return
         */
        public static HttpServletRequest getRequest() {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            return servletRequestAttributes.getRequest();
        }

        public static String getRequestBody(ServletInputStream servletInputStream) {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new InputStreamReader(servletInputStream, StandardCharsets.UTF_8));

                String line;
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException var16) {
                var16.printStackTrace();
            } finally {
                if (servletInputStream != null) {
                    try {
                        servletInputStream.close();
                    } catch (IOException var15) {
                        var15.printStackTrace();
                    }
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException var14) {
                        var14.printStackTrace();
                    }
                }

            }

            return sb.toString();
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
         * @return
         */
        public static HttpServletResponse getResponse() {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            return servletRequestAttributes.getResponse();
        }
    }
}
