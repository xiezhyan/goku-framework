package top.zopx.goku.framework.log.configurator.advice;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import top.zopx.goku.framework.log.constant.LogConstant;
import top.zopx.goku.framework.log.event.ErrorLogEvent;
import top.zopx.goku.framework.tools.entity.wrapper.R;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.web.context.GlobalContext;
import top.zopx.goku.framework.web.context.SpringContext;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理
 *
 * @author 俗世游子
 * @date 2021/4/12
 */
public abstract class BaseExceptionAdvice implements IMsg{

    @ExceptionHandler(BusException.class)
    public R<String> handleBusException(BusException e) {
        doAfter(e);
        return R.failure(getErrorMsg(e.getMsg(), e), e.getCode());
    }

    @ExceptionHandler(Exception.class)
    public R<String> handleException(Exception e) {
        doAfter(e);
        return R.failure(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<String> handleValidationException(MethodArgumentNotValidException e) {
        doAfter(e);
        return R.failure(getErrorMsg(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), e), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(BindException.class)
    public R<String> handleValidationException(BindException e) {
        doAfter(e);
        return R.failure(getErrorMsg(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), e), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(Throwable.class)
    public R<String> handleValidationException(Throwable e) {
        doAfter(e);
        return R.failure(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    public void doAfter(Throwable e) {
        publish(e);
    }

    private void publish(Throwable e) {
        HttpServletRequest request = GlobalContext.CurrentRequest.getRequest();
        Map<String, Object> map = new HashMap<>();
        if (ObjectUtils.isNotEmpty(e)) {
            map.put(LogConstant.STACK_TRACE, printStackTraceToString(e));
            map.put(LogConstant.EXCEPTION_NAME, e.getClass().getName());
            map.put(LogConstant.ERROR_MESSAGE, getErrorMsg(e.getMessage(), e));
            StackTraceElement[] elements = e.getStackTrace();
            if (ObjectUtils.isNotEmpty(elements)) {
                StackTraceElement element = elements[0];
                map.put(LogConstant.METHOD_NAME, element.getMethodName());
                map.put(LogConstant.CLASS_NAME, element.getClassName());
                map.put(LogConstant.FILE_NAME, element.getFileName());
                map.put(LogConstant.LINE_NUMBER, element.getLineNumber());
            }
        }
        addRequestInfo(request, map);
        SpringContext.publishEvent(new ErrorLogEvent(map));
    }

    private static void addRequestInfo(HttpServletRequest request, Map<String, Object> map) {
        map.put(LogConstant.REQUEST_URI, getPath(request.getRequestURI()));
        map.put(LogConstant.IP, GlobalContext.CurrentRequest.getBrowserIp());
        map.put(LogConstant.AGENT, GlobalContext.CurrentRequest.getBrowserAgent());
        map.put(LogConstant.REFERENCE, GlobalContext.CurrentRequest.getBrowserRefer());
        map.put(LogConstant.CREATE_TIME, LocalDateTime.now());
        map.put(LogConstant.REQUEST_TYPE, request.getMethod());
    }

    private static String getPath(String uriStr) {
        URI uri;
        try {
            uri = new URI(uriStr);
        } catch (URISyntaxException var3) {
            throw new RuntimeException(var3);
        }

        return uri.getPath();
    }

    private String printStackTraceToString(Throwable t) {
        try (StringWriter sw = new StringWriter()) {
            t.printStackTrace(new PrintWriter(sw, true));
            return sw.getBuffer().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

