package top.zopx.starter.log.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.zopx.starter.tools.configurator.basic.SquareProperties;

import java.lang.ref.PhantomReference;

/**
 * @author sanq.Yan
 * @date 2021/4/14
 */
@Configuration
@ConfigurationProperties(prefix = SquareLogProperties.PREFIX)
public class SquareLogProperties {
    public static final String PREFIX = "square.log";

    /**
     * 是否持久化
     */
    private boolean endurance = false;

    public boolean isEndurance() {
        return endurance;
    }

    public void setEndurance(boolean endurance) {
        this.endurance = endurance;
    }
}
