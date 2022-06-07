package top.zopx.goku.framework.material.configurator.minio.client;

import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.material.configurator.minio.properties.BootstrapMinIO;
import top.zopx.goku.framework.material.properties.BootstrapMaterial;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.tools.util.string.StringUtil;
import top.zopx.goku.framework.web.util.LogHelper;

import javax.annotation.Resource;
import java.util.Objects;

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
    private BootstrapMaterial bootstrapMaterial;

    @Bean
    @ConditionalOnProperty(BootstrapMinIO.PREFIX + ".endpoint")
    public MinIOMarker minIOMarker() {
        return new MinIOMarker();
    }

    @Bean
    @ConditionalOnBean(MinIOMarker.class)
    public MinioClient writeMinioClient() {
        LogHelper.getLogger(MinIOClientConfigurator.class).debug("MinioClient开始配置");

        final BootstrapMinIO bootstrapMinIO = bootstrapMaterial.getMinio();
        if (Objects.isNull(bootstrapMinIO)) {
            throw new BusException("MinIO配置不足");
        }
        boolean secure = bootstrapMinIO.getEndpoint().startsWith("https");
        MinioClient.Builder builder = MinioClient.builder()
                .credentials(bootstrapMinIO.getAppSecretId(), bootstrapMinIO.getAppSecretKey());

        if (StringUtil.isNotBlank(bootstrapMinIO.getRegion())) {
            builder = builder.region(bootstrapMinIO.getRegion());
        }

        if (Objects.isNull(bootstrapMinIO.getPort())) {
            return builder
                    .endpoint(bootstrapMinIO.getEndpoint(), secure ? 443 : 80, secure)
                    .build();
        } else {
            return builder
                    .endpoint(bootstrapMinIO.getEndpoint(), bootstrapMinIO.getPort(), secure)
                    .build();
        }
    }

    public static class MinIOMarker {}
}
