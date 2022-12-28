package top.zopx.goku.example.configurator;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zopx.goku.example.util.MessageUtil;
import top.zopx.goku.framework.log.configurator.advice.BaseExceptionAdvice;
import top.zopx.goku.framework.tools.exceptions.BusException;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/07/18 22:21
 */
@RestControllerAdvice
public class GlobalExceptionAdvice extends BaseExceptionAdvice {
    @Override
    public String getErrorMsg(String sourceMsg, Throwable e) {
        if (e instanceof BusException) {
            try {
                BusException busE = (BusException) e;
                return MessageUtil.getMessage(busE.getKey());
            } catch (Exception ex) {
                return super.getErrorMsg(sourceMsg, ex);
            }
        }
        try {
            return MessageUtil.getMessage(sourceMsg);
        } catch (Exception ex) {
            return super.getErrorMsg(sourceMsg, e);
        }
    }
}
