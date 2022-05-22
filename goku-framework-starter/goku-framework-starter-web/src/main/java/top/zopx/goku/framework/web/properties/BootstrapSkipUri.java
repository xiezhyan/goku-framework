package top.zopx.goku.framework.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

import static top.zopx.goku.framework.web.properties.BootstrapSkipUri.SKIP_URI;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 9:18
 */
@Component
@ConfigurationProperties(value = SKIP_URI)
@RefreshScope
public class BootstrapSkipUri {
    public static final String SKIP_URI = "goku.web.skip";

    private List<String> uri;

    public List<String> getUri() {
        return uri;
    }

    public void setUri(List<String> uri) {
        this.uri = uri;
    }
}
