package top.zopx.goku.framework.material.minio.client;

import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.material.minio.properties.BootstrapMinIO;

import javax.annotation.Resource;

import java.util.Objects;

import static top.zopx.goku.framework.material.minio.properties.BootstrapMinIO.PREFIX;

/**
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/30
 */
@SuppressWarnings("all")
@Component
public class MinIOClientConfigurator {

    @Resource
    private BootstrapMinIO bootstrapMinIO;

    @Bean
    @ConditionalOnMissingBean
    public MinioClient writeMinIOClient() {
        boolean secure = bootstrapMinIO.getEndpoint().startsWith("https");
        if (Objects.isNull(bootstrapMinIO.getPort())) {
            return MinioClient.builder()
                    .endpoint(bootstrapMinIO.getEndpoint(), secure ? 443 : 80, secure)
                    .credentials(bootstrapMinIO.getAppSecretId(), bootstrapMinIO.getAppSecretKey())
                    .build();
        } else {
            return MinioClient.builder()
                    .endpoint(bootstrapMinIO.getEndpoint(), bootstrapMinIO.getPort(), secure)
                    .credentials(bootstrapMinIO.getAppSecretId(), bootstrapMinIO.getAppSecretKey())
                    .build();
        }
    }
}
