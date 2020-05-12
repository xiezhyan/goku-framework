package top.zopx.starter.upload.auto;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import top.zopx.starter.upload.conditional.FastDFSConditional;
import top.zopx.starter.upload.conditional.OssConditional;
import top.zopx.starter.upload.config.FastDfsProperties;
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
@EnableConfigurationProperties({
        UploadProperties.class,
        FastDfsProperties.class
})
public class UploadAutoConfig {

    @Bean(name = "fileManageService")
    @Conditional(value = {
            OssConditional.class
    })
    public FileManageService ossFileManageService() {
        return new OssUploadManage();
    }

    @Bean(name = "fileManageService")
    @Conditional(value = {
            FastDFSConditional.class
    })
    public FileManageService fastDfsManageService() {
        return new FastDFSManageService();
    }

}
