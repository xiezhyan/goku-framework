package top.zopx.goku.framework.support.primary.configurator.marker;

import org.springframework.context.annotation.Bean;

/**
 * 标志位
 *
 * @author 俗世游子
 * @date 2021/12/25
 * @email xiezhyan@126.com
 */
public class ZookeeperMarkerConfiguration {
    @Bean
    public ZookeeperPrimaryMarker zookeeperUniqueMarker() {
        return new ZookeeperPrimaryMarker();
    }

    public static class ZookeeperPrimaryMarker {
    }
}
