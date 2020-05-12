package top.zopx.starter.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * top.zopx.starter.upload.config.NginxProperties
 *
 * @author sanq.Yan
 * @date 2020/5/12
 */
@Configuration
@Component
@ConfigurationProperties(prefix = "nginx")
@Data
public class NginxProperties {

    private String host;

    private int port;

    private String prefix = "http";
}
