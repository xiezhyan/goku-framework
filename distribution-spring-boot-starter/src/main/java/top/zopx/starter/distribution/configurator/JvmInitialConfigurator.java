package top.zopx.starter.distribution.configurator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import top.zopx.starter.distribution.properties.DistributionProperties;
import top.zopx.starter.distribution.properties.jvm.Jvm;

import javax.annotation.Resource;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sanq.Yan
 * @date 2021/4/3
 */
@ConditionalOnProperty(prefix = Jvm.PREFIX, name = "open", havingValue = "true")
public class JvmInitialConfigurator {

    @Resource
    private DistributionProperties distributionProperties;

    @Bean
    public ReentrantLock reentrantLock() {
        return new ReentrantLock(distributionProperties.getJvm().isFail());
    }

}
