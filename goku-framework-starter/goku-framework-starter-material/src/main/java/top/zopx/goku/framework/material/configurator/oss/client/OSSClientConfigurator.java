package top.zopx.goku.framework.material.configurator.oss.client;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.Protocol;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.material.configurator.oss.properties.BootstrapOSS;
import top.zopx.goku.framework.material.properties.BootstrapMaterial;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.web.util.LogHelper;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/30
 */
@SuppressWarnings("all")
@Component
public class OSSClientConfigurator {

    @Resource
    private BootstrapMaterial bootstrapMaterial;

    @Bean
    @ConditionalOnProperty(BootstrapOSS.PREFIX + ".endpoint")
    public OssMarker ossMarker() {
        return new OssMarker();
    }


    @Bean
    @ConditionalOnBean(OssMarker.class)
    @ConditionalOnMissingBean
    public OSS writeOSSClient(ClientBuilderConfiguration createOSSClientConfiguration) {
        LogHelper.getLogger(OSSClientConfigurator.class).debug("OSS开始配置");

        final BootstrapOSS bootstrapOSS = bootstrapMaterial.getOss();
        if (Objects.isNull(bootstrapOSS)) {
            throw new BusException("OSS配置不足");
        }
        // 设置连接OSS所使用的协议（HTTP或HTTPS），默认为HTTP。
        createOSSClientConfiguration.setProtocol(bootstrapOSS.getSupportHttps() ? Protocol.HTTPS : Protocol.HTTP);
        return new OSSClientBuilder()
                .build(
                        bootstrapOSS.getEndpoint(),
                        bootstrapOSS.getAppSecretId(),
                        bootstrapOSS.getAppSecretKey(),
                        createOSSClientConfiguration
                );
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientBuilderConfiguration createOSSClientConfiguration() {
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        // 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
        conf.setMaxConnections(2048);
        // 设置Socket层传输数据的超时时间，默认为50000毫秒。
        conf.setSocketTimeout(50000);
        // 设置建立连接的超时时间，默认为50000毫秒。
        conf.setConnectionTimeout(50000);
        // 设置从连接池中获取连接的超时时间（单位：毫秒），默认不超时。
        conf.setConnectionRequestTimeout(6000);
        // 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
        conf.setIdleConnectionTime(60000);
        // 设置失败请求重试次数，默认为3次。
        conf.setMaxErrorRetry(5);
        // 设置是否支持将自定义域名作为Endpoint，默认支持。
        conf.setSupportCname(true);
        return conf;
    }


    public static class OssMarker {}
}
