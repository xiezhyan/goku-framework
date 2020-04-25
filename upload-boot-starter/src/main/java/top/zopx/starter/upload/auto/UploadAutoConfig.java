package top.zopx.starter.upload.auto;

import com.aliyun.oss.OSS;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import top.zopx.starter.upload.config.UploadProperties;
import top.zopx.starter.upload.service.FileManageService;
import top.zopx.starter.upload.service.impl.OssUploadManage;

/**
 * top.zopx.starter.upload.auto.UploadAutoConfig
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
@Configuration
@EnableConfigurationProperties(UploadProperties.class)
public class UploadAutoConfig {

    @Bean
    @ConditionalOnClass(OSS.class)
    public FileManageService ossFileManageService() {
        return new OssUploadManage();
    }
}
