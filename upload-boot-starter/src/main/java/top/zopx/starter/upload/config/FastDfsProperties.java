package top.zopx.starter.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * top.zopx.starter.upload.config.NginxProperties
 *
 * @author sanq.Yan
 * @date 2020/5/12
 */
@Configuration
@Component
@ConfigurationProperties(prefix = FastDfsProperties.PREFIX)
@Data
public class FastDfsProperties {

    public static final String PREFIX = "fdfs";

    private NginxProperties nginx;

    private List<String> trackerList;

    @Data
    @Component
    @ConfigurationProperties(prefix = FastDfsProperties.NginxProperties.PREFIX)
    public static class NginxProperties {
        public static final String PREFIX = FastDfsProperties.PREFIX + ".nginx";

        private String host;
        private int port;
        private String prefix = "http";
    }

}
