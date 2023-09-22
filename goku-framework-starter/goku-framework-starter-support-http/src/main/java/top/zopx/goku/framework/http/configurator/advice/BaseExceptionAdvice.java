package top.zopx.goku.framework.http.configurator.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import top.zopx.goku.framework.http.configurator.record.LogEvent;
import top.zopx.goku.framework.http.constant.LogConstant;
import top.zopx.goku.framework.http.context.GlobalContext;
import top.zopx.goku.framework.http.context.SpringContext;
import top.zopx.goku.framework.tools.entity.wrapper.R;
import top.zopx.goku.framework.tools.exception.BusException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理
 *
 * @author Mr.Xie
 * @date 2021/4/12
 */
public abstract class BaseExceptionAdvice implements IMsg{

    @ExceptionHandler(BusException.class)
    public R<String> handleBusException(BusException e) {
        return buildR(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<String> handleValidationException(MethodArgumentNotValidException e) {
        return buildR(e);
    }

    @ExceptionHandler(BindException.class)
    public R<String> handleValidationException(BindException e) {
        return buildR(e);
    }

    @ExceptionHandler(Throwable.class)
    public R<String> handleValidationException(Throwable e) {
        return buildR(e);
    }

    protected R<String> buildR(Throwable e) {
        doAfter(e);
        if (e instanceof BusException busE) {
            return R.failure(getErrorMsg(busE.getMsg(), busE.getFormat()), busE.getCode());
        } else if (e instanceof MethodArgumentNotValidException validE) {
            return R.failure(getErrorMsg(validE.getBindingResult().getAllErrors().get(0).getDefaultMessage()), HttpStatus.INTERNAL_SERVER_ERROR.value());
        } else if (e instanceof BindException bindE) {
            return R.failure(getErrorMsg(bindE.getBindingResult().getAllErrors().get(0).getDefaultMessage()), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return R.failure(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    protected void doAfter(Throwable e) {
        publish(e);
    }

    protected void publish(Throwable e) {
        HttpServletRequest request = GlobalContext.CurrentRequest.getRequest();
        Map<String, Object> map = new HashMap<>();
        if (ObjectUtils.isNotEmpty(e)) {
            map.put(LogConstant.STACK_TRACE, printStackTraceToString(e));
            map.put(LogConstant.EXCEPTION_NAME, e.getClass().getName());
            map.put(LogConstant.ERROR_MESSAGE, getErrorMsg(e.getMessage()));
            StackTraceElement[] elements = e.getStackTrace();
            if (ObjectUtils.isNotEmpty(elements)) {
                StackTraceElement element = elements[0];
                map.put(LogConstant.METHOD_NAME, element.getMethodName());
                map.put(LogConstant.CLASS_NAME, element.getClassName());
                map.put(LogConstant.FILE_NAME, element.getFileName());
                map.put(LogConstant.LINE_NUMBER, element.getLineNumber());
            }
        }
        LogEvent.addRequestInfo(request, map);
        map.put(LogConstant.LOG_TYPE, "exception");
        SpringContext.publishEvent(new LogEvent(map));
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

