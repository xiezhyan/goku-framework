package top.zopx.goku.example.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * <p>Accept-Language</p>
 * <ul>
 *     <li>en-US</li>
 *     <li>zh-CN</li>
 * </ul>
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/14 17:58
 */
@Component
public final class MessageUtil {

    private static MessageSource messageSource;

    public MessageUtil(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }

    public static String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
