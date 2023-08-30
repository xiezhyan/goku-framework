package top.zopx.goku.framework.http.util.i18n;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <p>Accept-Language</p>
 * <ul>
 *     <li>en-US</li>
 *     <li>zh-CN</li>
 * </ul>
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 17:58
 */
@ConditionalOnBean(MessageSourceAccessor.class)
@Component
public final class MessageUtil {

    private static MessageSourceAccessor messageSourceAccessor;

    private static final MessageSourceAccessor DEFAULT = CustomMessageSourceAccessor.getAccessor("");

    public MessageUtil(MessageSourceAccessor messageSourceAccessor) {
        MessageUtil.messageSourceAccessor = messageSourceAccessor;
    }

    public static String getMessage(String key) {
        return Optional.ofNullable(messageSourceAccessor).orElse(DEFAULT)
                .getMessage(key, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String key, String defaultMsg) {
        return Optional.ofNullable(messageSourceAccessor).orElse(DEFAULT)
                .getMessage(key, defaultMsg, LocaleContextHolder.getLocale());
    }
}
