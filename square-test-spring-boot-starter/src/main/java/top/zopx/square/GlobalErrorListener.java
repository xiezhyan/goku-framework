package top.zopx.square;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zopx.starter.log.configurator.mvc.advice.ExceptionAdvice;
import top.zopx.starter.tools.basic.R;
import top.zopx.starter.tools.tools.web.LogUtil;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/30 9:32
 */
@RestControllerAdvice
public class GlobalErrorListener extends ExceptionAdvice {

    @ExceptionHandler({ArrException.class})
    public R<String> handleOnArrException(ArrException e) {
        LogUtil.getInstance(GlobalErrorListener.class).error("ArrException异常信息：{}", e.getMessage());
        this.publish(e);
        return R.failure(e.getMessage(), 600);
    }
}
