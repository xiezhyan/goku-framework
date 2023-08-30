package top.zopx.goku.framework.redis.bus.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.time.Duration;

import static top.zopx.goku.framework.redis.bus.properties.BootstrapCode.PREFIX;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/6/7 21:13
 */
@Configuration
@ConfigurationProperties(PREFIX)
public class BootstrapCode implements Serializable {
    public static final String PREFIX = "goku.bus.code";

    /**
     * code长度
     */
    private Integer countLength = 6;

    /**
     * 自定义字符串生成内容
     */
    private String charactor;

    /**
     * 过期时间 默认30分钟
     */
    private Duration expireTime = Duration.ofMinutes(30L);

    public Integer getCountLength() {
        return countLength;
    }

    public void setCountLength(Integer countLength) {
        this.countLength = countLength;
    }

    public String getCharactor() {
        return charactor;
    }

    public void setCharactor(String charactor) {
        this.charactor = charactor;
    }

    public Duration getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Duration expireTime) {
        this.expireTime = expireTime;
    }
}
