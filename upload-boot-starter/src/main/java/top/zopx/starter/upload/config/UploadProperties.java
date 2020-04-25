package top.zopx.starter.upload.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * top.zopx.starter.upload.config
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
@ConfigurationProperties(prefix = "upload")
@Configuration
@Component
@Data
public class UploadProperties {

    public static final String PREFIX = "upload";

    private OssProperties oss;

    @Data
    @Component
    @ConfigurationProperties(prefix = UploadProperties.PREFIX + "." + OssProperties.PREFIX)
    public static class OssProperties {

        public static final String PREFIX = "oss";

        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private String bucketName;
        private String showImgUrlPrefix;
        // 是否授权访问
        private Presign presign;

        @Data
        public static class Presign {
            private Boolean presigned = false;
            private Long presignTime = 3600L;
        }

        @Bean
        @ConditionalOnProperty(prefix = UploadProperties.PREFIX + "." + OssProperties.PREFIX, name = {"endpoint", "access-key-id", "access-key-secret", "bucket-name"})
        public OSS ossClient() {
            return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        }
    }
}
