package top.zopx.starter.upload.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import top.zopx.starter.upload.conditional.OssConditional;

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
    @ConfigurationProperties(prefix = OssProperties.PREFIX)
    public static class OssProperties {

        public static final String PREFIX = UploadProperties.PREFIX + "." + "oss";

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
        @Conditional(value = {
                OssConditional.class
        })
        public OSS ossClient() {
            return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        }
    }
}
