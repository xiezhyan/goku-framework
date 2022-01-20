package top.zopx.starter.log.configurator.mvc.advice;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zopx.starter.log.event.listener.error.ErrorPublish;
import top.zopx.starter.tools.basic.R;
import top.zopx.starter.tools.exceptions.BusException;
import top.zopx.starter.tools.tools.json.impl.FJsonUtil;
import top.zopx.starter.tools.tools.web.LogUtil;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sanq.Yan
 * @date 2021/4/12
 */
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BusException.class)
    public R<String> handleBusException(BusException e) {
        LogUtil.getInstance(ExceptionAdvice.class).error("BusException异常信息：{}", e.getMessage());
        publish(e);
        return R.failure(e.getMsg(), e.getCode());
    }

    @ExceptionHandler(Exception.class)
    public R<String> handleException(Exception e) {
        LogUtil.getInstance(ExceptionAdvice.class).error("通用异常信息：{}", e.getMessage());
        publish(e);
        return R.failure(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<String> handleValidationException(MethodArgumentNotValidException e) {
        LogUtil.getInstance(ExceptionAdvice.class).error("请求参数校验异常信息：{}", e.getMessage());
        publish(e);
        return R.failure(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.FORBIDDEN.value());

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public R<String> handleValidationException(ConstraintViolationException e) {
        LogUtil.getInstance(ExceptionAdvice.class).error("校验参数异常信息：{}", e.getMessage());
        publish(e);
        return R.failure(e.getMessage(), HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(BindException.class)
    public R<String> handleValidationException(BindException e) {
        LogUtil.getInstance(ExceptionAdvice.class).error("校验参数异常信息：{}", e.getMessage());
        Map<String, String> errorMap = new HashMap<>();
        FieldError fieldError;
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            fieldError = (FieldError) error;
            errorMap.put(fieldError.getObjectName() + "." + fieldError.getField(), error.getDefaultMessage());
        }
        publish(e);
        return R.failure(FJsonUtil.INSTANCE.toJson(errorMap), HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(Throwable.class)
    public R<String> handleValidationException(Throwable e) {
        publish(e);
        return R.failure(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    public void publish(Throwable e) {
        ErrorPublish.publish(e);
    }
}

