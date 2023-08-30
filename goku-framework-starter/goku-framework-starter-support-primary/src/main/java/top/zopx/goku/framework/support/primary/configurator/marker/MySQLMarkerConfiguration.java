package top.zopx.goku.framework.support.primary.configurator.marker;

import org.springframework.context.annotation.Bean;

/**
 * 标志位
 *
 * @author Mr.Xie
 * @date 2021/12/25
 * @email xiezhyan@126.com
 */
public class MySQLMarkerConfiguration {
    @Bean
    public MySQLPrimaryMarker mySQLUniqueMarker() {
        return new MySQLPrimaryMarker();
    }

    public static class MySQLPrimaryMarker {
    }
}
