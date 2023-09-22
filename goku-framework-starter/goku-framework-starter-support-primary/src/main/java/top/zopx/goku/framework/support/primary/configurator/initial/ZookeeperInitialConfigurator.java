package top.zopx.goku.framework.support.primary.configurator.initial;

import jakarta.annotation.Resource;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.RetryNTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import top.zopx.goku.framework.support.primary.configurator.marker.ZookeeperMarkerConfiguration;
import top.zopx.goku.framework.support.primary.core.service.IRegisterNodeService;
import top.zopx.goku.framework.support.primary.properties.BootstrapSnowflake;
import top.zopx.goku.framework.support.primary.snowflake.service.IDSnowflakeGetterService;
import top.zopx.goku.framework.support.primary.snowflake.service.RegisterNodeService;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;


/**
 *
 * @author Mr.Xie
 * @date 2021/12/25
 * @email xiezhyan@126.com
 */
public class ZookeeperInitialConfigurator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperInitialConfigurator.class);

    @Resource
    private BootstrapSnowflake bootstrapSnowflake;

    /**
     * CuratorFramework 加载配置
     *
     * @return CuratorFramework
     */
    @Bean
    @ConditionalOnBean({ZookeeperMarkerConfiguration.ZookeeperPrimaryMarker.class})
    public CuratorFramework curatorFramework() {
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                bootstrapSnowflake.getZookeeperHost(),
                new RetryNTimes(bootstrapSnowflake.getRetry(), bootstrapSnowflake.getSleepMsBetweenRetries())
        );

        client.start();
        if (client.getState() == CuratorFrameworkState.STARTED) {
            LOGGER.info("zk client start successfully!");
            LOGGER.info("zkAddress:{}, serverName={}", bootstrapSnowflake.getZookeeperHost(), bootstrapSnowflake.getServerName());
        } else {
            throw new BusException("客户端启动失败。。。", IBus.ERROR_CODE);
        }
        return client;
    }

    @Bean
    @ConditionalOnBean({CuratorFramework.class})
    public IRegisterNodeService registerNodeService(CuratorFramework curatorFramework) {
        return new RegisterNodeService(curatorFramework, bootstrapSnowflake.getServerName());
    }

    @Bean(initMethod = "init")
    @ConditionalOnBean({IRegisterNodeService.class})
    public IDSnowflakeGetterService snowflakeGetterService(IRegisterNodeService registerNodeService) {
        return new IDSnowflakeGetterService(registerNodeService, bootstrapSnowflake.getSnowflake());
    }
}
