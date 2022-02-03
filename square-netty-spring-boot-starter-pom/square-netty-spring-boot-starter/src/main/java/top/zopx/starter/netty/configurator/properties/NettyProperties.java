package top.zopx.starter.netty.configurator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import top.zopx.square.netty.configurator.properties.NettyServerConfig;

/**
 * @author 俗世游子
 * @date 2022/2/3
 * @email xiezhyan@126.com
 */
@ConfigurationProperties("square.netty")
public class NettyProperties extends NettyServerConfig {
}
