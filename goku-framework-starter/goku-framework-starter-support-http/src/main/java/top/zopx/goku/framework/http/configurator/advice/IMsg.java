package top.zopx.goku.framework.http.configurator.advice;

import top.zopx.goku.framework.http.util.i18n.MessageUtil;

import java.text.MessageFormat;

/**
 * 对消息进行处理：国际化
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/09/11 20:15
 */
public interface IMsg {
    /**
     * 对消息进行处理
     *
     * @param sourceMsg 原始消息
     * @return 新消息
     */
    default String getErrorMsg(String sourceMsg, Object... format) {
        try {
            String message = MessageUtil.getMessage(sourceMsg);
            return MessageFormat.format(message, format);
        } catch (Exception e) {
            return sourceMsg;
        }
    }
}
