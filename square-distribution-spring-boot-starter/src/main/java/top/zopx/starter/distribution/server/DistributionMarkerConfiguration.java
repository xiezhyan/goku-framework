package top.zopx.starter.distribution.server;

import org.springframework.context.annotation.Bean;

/**
 * @author sanq.Yan
 * @date 2021/3/29
 */
public class DistributionMarkerConfiguration {

    @Bean
    public DistributionMarker distributionServerMarkerBean() {
        return new DistributionMarker();
    }

    static class DistributionMarker {
    }
}
