package top.zopx.starter.log.advice;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zopx.starter.log.listener.ErrorPublish;
import top.zopx.starter.tools.basic.Response;
import top.zopx.starter.tools.constants.BusCode;
import top.zopx.starter.tools.exceptions.BusException;
import top.zopx.starter.tools.tools.json.JsonUtil;
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
    public Response handleBusException(BusException e) {
        LogUtil.getInstance(ExceptionAdvice.class).error("BusException异常信息：{}", e.getMessage());
        publish(e);
        return new Response().failure(e.getMsg(), e.getCode());
    }

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        LogUtil.getInstance(ExceptionAdvice.class).error("通用异常信息：{}", e.getMessage());
        publish(e);
        return new Response().failure(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleValidationException(MethodArgumentNotValidException e) {
        LogUtil.getInstance(ExceptionAdvice.class).error("请求参数校验异常信息：{}", e.getMessage());
        publish(e);
        return new Response().failure(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), BusCode.PARAM_VALIDATE_ERROR);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Response handleValidationException(ConstraintViolationException e) {
        LogUtil.getInstance(ExceptionAdvice.class).error("校验参数异常信息：{}", e.getMessage());
        publish(e);
        return new Response().failure(e.getMessage(), BusCode.PARAM_VALIDATE_ERROR);
    }

    @ExceptionHandler(BindException.class)
    public Response handleValidationException(BindException e) {
        LogUtil.getInstance(ExceptionAdvice.class).error("校验参数异常信息：{}", e.getMessage());
        Map<String, String> errorMap = new HashMap<>();
        FieldError fieldError;
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            fieldError = (FieldError) error;
            errorMap.put(fieldError.getObjectName() + "." + fieldError.getField(), error.getDefaultMessage());
        }
        publish(e);
        return new Response().failure(JsonUtil.toJson(errorMap), BusCode.PARAM_VALIDATE_ERROR);
    }

    @ExceptionHandler(Throwable.class)
    public Response handleValidationException(Throwable e) {
        publish(e);
        return new Response().failure(e.getMessage(), BusCode.RESULT_ERROR);
    }

    public void publish(Throwable e) {
        ErrorPublish.publish(e);
    }
}

