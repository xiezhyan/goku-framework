package top.zopx.starter.distribution.properties.jvm;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import top.zopx.starter.distribution.properties.SquareDistributionProperties;

/**
 * @author sanq.Yan
 * @date 2021/4/3
 */
@Component
@Configuration
@ConfigurationProperties(Jvm.PREFIX)
public class Jvm {
    public static final String PREFIX = SquareDistributionProperties.DISTRIBUTION_PREFIX + ".jvm";


    /**
     * 是否启动,默认启动
     */
    private boolean open = true;

    /**
     * 是否为公平锁
     */
    private boolean fail = true;

    public boolean isFail() {
        return fail;
    }

    public void setFail(boolean fail) {
        this.fail = fail;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
