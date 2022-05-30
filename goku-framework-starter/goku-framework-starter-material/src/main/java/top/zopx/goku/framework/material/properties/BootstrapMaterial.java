package top.zopx.goku.framework.material.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.material.configurator.minio.properties.BootstrapMinIO;
import top.zopx.goku.framework.material.configurator.oss.properties.BootstrapOSS;

import static top.zopx.goku.framework.material.properties.BootstrapMaterial.PREFIX;
/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 9:31
 */
@Configuration
@Component
@ConfigurationProperties(value = PREFIX)
public class BootstrapMaterial {

    public static final String PREFIX = "goku.material";

    private BootstrapMinIO minio;

    private BootstrapOSS oss;

    public BootstrapMinIO getMinio() {
        return minio;
    }

    public void setMinio(BootstrapMinIO minio) {
        this.minio = minio;
    }

    public BootstrapOSS getOss() {
        return oss;
    }

    public void setOss(BootstrapOSS oss) {
        this.oss = oss;
    }

}
