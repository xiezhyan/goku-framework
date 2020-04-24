package top.zopx.starter.tools.advice;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zopx.starter.tools.basic.Response;
import top.zopx.starter.tools.constants.BusCode;
import top.zopx.starter.tools.exceptions.BusException;

import javax.validation.ConstraintViolationException;

/**
 * version: 统一异常处理
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
@RestControllerAdvice   // 控制器增强
public class ExceptionAdvice {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        e.printStackTrace();
        return new Response().failure(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        return new Response().failure(e.getMessage());
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public Response handleHttpMediaTypeNotSupportedException(Exception e) {
        e.printStackTrace();
        return new Response().failure(e.getMessage());
    }

    @ExceptionHandler(BusException.class)
    public Response handleBusException(BusException e) {
        e.printStackTrace();
        return new Response().failure(e.getMsg(), e.getCode());
    }

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        e.printStackTrace();
        return new Response().failure(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleValidationException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        return new Response().failure(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), BusCode.VALIDATION_CODE);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Response handleValidationException(ConstraintViolationException e) {
        e.printStackTrace();
        return new Response().failure(e.getConstraintViolations().iterator().next().getMessage(), BusCode.VALIDATION_CODE);
    }

    @ExceptionHandler(BindException.class)
    public Response handleValidationException(BindException e) {
        e.printStackTrace();
        return new Response().failure(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), BusCode.VALIDATION_CODE);
    }
}
