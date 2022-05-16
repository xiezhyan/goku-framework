package top.zopx.starter.log.configurator.mvc.advice;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zopx.starter.log.constant.LogConstant;
import top.zopx.starter.log.event.ErrorLogEvent;
import top.zopx.starter.log.util.SpringUtil;
import top.zopx.starter.tools.basic.R;
import top.zopx.starter.tools.exceptions.BusException;
import top.zopx.starter.tools.tools.web.GlobalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 俗世游子
 * @date 2021/4/12
 */
@RestControllerAdvice
public class ExceptionAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(BusException.class)
    public R<String> handleBusException(BusException e) {
        LOGGER.error("BusException异常信息：{}", e.getMessage());
        publish(e);
        return R.failure(e.getMsg(), e.getCode());
    }

    @ExceptionHandler(Exception.class)
    public R<String> handleException(Exception e) {
        LOGGER.error("通用异常信息：{}", e.getMessage());
        publish(e);
        return R.failure(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<String> handleValidationException(MethodArgumentNotValidException e) {
        LOGGER.error("请求参数校验异常信息：{}", e.getMessage());
        publish(e);
        return R.failure(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.FORBIDDEN.value());

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public R<String> handleValidationException(ConstraintViolationException e) {
        LOGGER.error("校验参数异常信息：{}", e.getMessage());
        publish(e);
        return R.failure(e.getMessage(), HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(BindException.class)
    public R<String> handleValidationException(BindException e) {
        LOGGER.error("校验参数异常信息：{}", e.getMessage());
        Map<String, String> errorMap = new HashMap<>();
        FieldError fieldError;
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            fieldError = (FieldError) error;
            errorMap.put(fieldError.getObjectName() + "." + fieldError.getField(), error.getDefaultMessage());
        }
        publish(e);
        return R.failure(SpringUtil.getJson().toJson(errorMap), HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(Throwable.class)
    public R<String> handleValidationException(Throwable e) {
        publish(e);
        return R.failure(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    public void publish(Throwable e) {
        HttpServletRequest request = GlobalUtil.CurrentRequest.getRequest();
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

    public String printStackTraceToString(Throwable t) {
        try (StringWriter sw = new StringWriter()) {
            t.printStackTrace(new PrintWriter(sw, true));
            return sw.getBuffer().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

