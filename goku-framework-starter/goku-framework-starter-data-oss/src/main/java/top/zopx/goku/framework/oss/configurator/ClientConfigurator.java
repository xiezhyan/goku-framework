package top.zopx.goku.framework.oss.configurator;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zopx.goku.framework.oss.properties.BootstrapOSS;
import top.zopx.goku.framework.oss.service.OSSTemplate;
import top.zopx.goku.framework.oss.service.impl.OSSTemplateImpl;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/22
 */
@Configuration
@EnableConfigurationProperties(BootstrapOSS.class)
public class ClientConfigurator {

    @Bean
    @ConditionalOnMissingBean
    public AmazonS3 amazonS3(BootstrapOSS bootstrapOSS) {
        // 客户端配置，主要是全局的配置信息
        ClientConfiguration configuration = buildClientConfiguration(bootstrapOSS.getClient());
        // url以及region配置
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(bootstrapOSS.getEndPoint(), bootstrapOSS.getRegion());
        // 凭证配置
        BasicAWSCredentials credentials = new BasicAWSCredentials(bootstrapOSS.getAccessKey(), bootstrapOSS.getSecretKey());
        // build amazonS3Client客户端
        return AmazonS3Client.builder()
                .withClientConfiguration(configuration)
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .disableChunkedEncoding()
                .withPathStyleAccessEnabled(bootstrapOSS.getPathStyle())
                .build();
    }

    private ClientConfiguration buildClientConfiguration(BootstrapOSS.BootstrapClient client) {
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setMaxConnections(client.getMaxConnections());
        configuration.setRequestTimeout(client.getRequestTimeout());
        configuration.setConnectionTimeout(client.getConnectionTimeout());
        configuration.setSocketTimeout(client.getSocketTimeout());
        configuration.setConnectionTTL(client.getConnectionTTL());
        return configuration;
    }

    @Bean
    public OSSTemplate ossTemplate(AmazonS3 amazonS3) {
        return new OSSTemplateImpl(amazonS3);
    }
}
