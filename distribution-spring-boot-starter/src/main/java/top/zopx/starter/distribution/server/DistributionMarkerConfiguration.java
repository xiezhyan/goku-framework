package top.zopx.starter.distribution.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sanq.Yan
 * @date 2021/3/29
 */
@Configuration(proxyBeanMethods = false)
public class DistributionMarkerConfiguration {

    @Bean
    public Marker distributionServerMarkerBean() {
        return new Marker();
    }

    static class Marker {
    }
}
