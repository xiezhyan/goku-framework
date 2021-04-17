package top.zopx.starter.log.listener.error;

import org.apache.commons.lang3.ObjectUtils;
import top.zopx.starter.log.constant.LogConstant;
import top.zopx.starter.log.event.ErrorLogEvent;
import top.zopx.starter.log.util.SpringUtil;
import top.zopx.starter.tools.tools.web.GlobalUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
        if (ObjectUtils.isNotEmpty(e)) {
            map.put(LogConstant.STACK_TRACE, printStackTraceToString(e));
            map.put(LogConstant.EXCEPTION_NAME, e.getClass().getName());
            map.put(LogConstant.ERROR_MESSAGE, e.getMessage());
            StackTraceElement[] elements = e.getStackTrace();
            if (ObjectUtils.isNotEmpty(elements)) {
                StackTraceElement element = elements[0];
                map.put(LogConstant.METHOD_NAME, element.getMethodName());
                map.put(LogConstant.CLASS_NAME, element.getClassName());
                map.put(LogConstant.FILE_NAME, element.getFileName());
                map.put(LogConstant.LINE_NUMBER, element.getLineNumber());
            }
        }
        SpringUtil.addRequestInfo(request, map);
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
}
