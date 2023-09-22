package top.zopx.goku.framework.http.util.i18n;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/6/3 10:50
 */
public class CustomMessageSourceAccessor extends ResourceBundleMessageSource {

    public CustomMessageSourceAccessor(String basename) {
        List<String> basenames = new ArrayList<>();
        basenames.add("top.zopx.goku.framework.http.messages");
        if (StringUtils.isNotBlank(basename)) {
            basenames.addAll(Arrays.asList(basename.split(",")));
        }
        setBasenames(basenames.toArray(new String[0]));
    }

    public static MessageSourceAccessor getAccessor(String basename) {
        return new MessageSourceAccessor(new CustomMessageSourceAccessor(basename));
    }
}
