package top.zopx.starter.log.listener;

import org.apache.commons.lang3.ObjectUtils;
import top.zopx.starter.log.constant.BaseConstant;
import top.zopx.starter.log.constant.ErrorConstant;
import top.zopx.starter.log.event.ErrorLogEvent;
import top.zopx.starter.log.util.SpringUtil;
import top.zopx.starter.tools.tools.date.LocalDateUtils;
import top.zopx.starter.tools.tools.web.GlobalUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sanq.Yan
 * @date 2021/4/12
 */
public class ErrorPublish {

    public static void publish(Throwable e) {
        HttpServletRequest request = GlobalUtil.Request.getRequest();
        Map<String, Object> map = new HashMap<>();
        map.put(ErrorConstant.REQUEST_URI, getPath(request.getRequestURI()));
        if (ObjectUtils.isNotEmpty(e)) {
            map.put(ErrorConstant.STACK_TRACE, printStackTraceToString(e));
            map.put(ErrorConstant.EXCEPTION_NAME, e.getClass().getName());
            map.put(ErrorConstant.ERROR_MESSAGE, e.getMessage());
            StackTraceElement[] elements = e.getStackTrace();
            if (ObjectUtils.isNotEmpty(elements)) {
                StackTraceElement element = elements[0];
                map.put(ErrorConstant.METHOD_NAME, element.getMethodName());
                map.put(ErrorConstant.CLASS_NAME, element.getClassName());
                map.put(ErrorConstant.FILE_NAME, element.getFileName());
                map.put(ErrorConstant.LINE_NUMBER, element.getLineNumber());
            }
        }
        addRequestInfo(request, map);
        SpringUtil.publishEvent(new ErrorLogEvent(map));
    }

    public static String printStackTraceToString(Throwable t) {
        try (StringWriter sw = new StringWriter()) {
            t.printStackTrace(new PrintWriter(sw, true));
            return sw.getBuffer().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static void addRequestInfo(HttpServletRequest request, Map<String, Object> map) {
        map.put(BaseConstant.IP, GlobalUtil.Request.getBrowserIp(request));
        map.put(BaseConstant.AGENT, GlobalUtil.Request.getBrowserAgent(request));
        map.put(BaseConstant.REFERENCE, GlobalUtil.Request.getBrowserRefer(request));
        map.put(BaseConstant.CREATE_TIME, LocalDateUtils.nowDate());
    }

    public static String getPath(String uriStr) {
        URI uri;
        try {
            uri = new URI(uriStr);
        } catch (URISyntaxException var3) {
            throw new RuntimeException(var3);
        }

        return uri.getPath();
    }
}
