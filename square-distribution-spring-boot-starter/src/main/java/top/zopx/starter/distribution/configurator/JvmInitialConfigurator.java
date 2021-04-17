package top.zopx.starter.distribution.configurator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import top.zopx.starter.distribution.properties.SquareDistributionProperties;

import javax.annotation.Resource;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sanq.Yan
 * @date 2021/4/3
 */
@ConditionalOnMissingBean({RedisInitialConfigurator.class, ZookeeperInitialConfigurator.class})
public class JvmInitialConfigurator {

    @Resource
    private SquareDistributionProperties squareDistributionProperties;

    @Bean
    public ReentrantLock reentrantLock() {
        return new ReentrantLock(squareDistributionProperties.getJvm().isFail());
    }

}
