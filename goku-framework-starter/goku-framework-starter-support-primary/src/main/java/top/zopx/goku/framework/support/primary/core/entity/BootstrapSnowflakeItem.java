package top.zopx.goku.framework.support.primary.core.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

import static top.zopx.goku.framework.support.primary.core.entity.BootstrapSnowflakeItem.PREFIX;

/**
 * @author Mr.Xie
 * @date 2022/1/24
 * @email xiezhyan@126.com
 */
@Configuration
@ConfigurationProperties(value = PREFIX)
public class BootstrapSnowflakeItem implements Serializable {
    public static final String PREFIX = "goku.primary.snowflake.snowflake";
    /**
     * 服务IP
     */
    private String host;

    /**
     * 服务端口
     */
    private Integer port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
