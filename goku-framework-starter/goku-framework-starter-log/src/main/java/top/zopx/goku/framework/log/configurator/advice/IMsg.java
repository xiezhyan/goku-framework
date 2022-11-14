package top.zopx.goku.framework.log.configurator.advice;

/**
 * 对消息进行处理：国际化
 *
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/09/11 20:15
 */
public interface IMsg {
    /**
     * 对消息进行处理
     *
     * @param sourceMsg 原始消息
     * @param e         异常类型
     * @return 新消息
     */
    default String getErrorMsg(String sourceMsg, Throwable e) {
        return sourceMsg;
    }
}
