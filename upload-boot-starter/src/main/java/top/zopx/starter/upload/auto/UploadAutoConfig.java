package top.zopx.starter.upload.auto;

import com.aliyun.oss.OSS;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zopx.starter.upload.config.FastDFSConfig;
import top.zopx.starter.upload.config.NginxProperties;
import top.zopx.starter.upload.config.UploadProperties;
import top.zopx.starter.upload.service.FileManageService;
import top.zopx.starter.upload.service.impl.FastDFSManageService;
import top.zopx.starter.upload.service.impl.OssUploadManage;

/**
 * top.zopx.starter.upload.auto.UploadAutoConfig
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
@Configuration
public class UploadAutoConfig {

    @Bean
    @ConditionalOnClass(value = {
            OSS.class
    })
    public FileManageService ossFileManageService() {
        return new OssUploadManage();
    }

    @Bean
    @ConditionalOnClass(value = {
            FastDFSConfig.class,
            NginxProperties.class
    })
    public FileManageService fastDfsManageService() {
        return new FastDFSManageService();
    }

}
