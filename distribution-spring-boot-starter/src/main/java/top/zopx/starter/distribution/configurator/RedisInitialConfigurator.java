package top.zopx.starter.distribution.configurator;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import top.zopx.starter.distribution.properties.DistributionProperties;
import top.zopx.starter.distribution.properties.redis.Redis;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sanq.Yan
 * @date 2021/3/28
 */
@ConditionalOnProperty(prefix = Redis.PREFIX, name = "open", havingValue = "true")
public class RedisInitialConfigurator {
    @Resource
    private DistributionProperties distributionProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 根据不同情况使用不同类型
        updateServer(config);

        return Redisson.create(config);
    }

    private void updateServer(Config config) {
        // 单机
        updateSingleServer(config);
        // 哨兵
        updateSentinelServer(config);
        // 集群
        updateClusterServer(config);
        // 主从
        updateMasterSlaveServer(config);
    }

    private void updateMasterSlaveServer(Config config) {
        if (null != distributionProperties.getRedis().getMasterSlave()) {
            config.useMasterSlaveServers()
                    .setDatabase(0)
                    .setRetryAttempts(distributionProperties.getRetryAttempts())
                    .setRetryInterval(distributionProperties.getRedis().getInterval())
                    .setMasterAddress(distributionProperties.getRedis().getAddress().get(0))
                    .addSlaveAddress(distributionProperties.getRedis().getMasterSlave().getSlaves().toArray(new String[0]));

            if (StringUtils.isNotBlank(distributionProperties.getRedis().getPassword())) {
                config.useMasterSlaveServers()
                        .setPassword(distributionProperties.getRedis().getPassword())
                ;
            }
        }
    }

    private void updateClusterServer(Config config) {
        List<String> address;
        if (1 < (address = distributionProperties.getRedis().getAddress()).size()) {
            config.useClusterServers()
                    .setScanInterval(distributionProperties.getRedis().getInterval())
                    .addNodeAddress(address.toArray(new String[0]))
                    .setRetryAttempts(distributionProperties.getRetryAttempts())
            ;
            if (StringUtils.isNotBlank(distributionProperties.getRedis().getPassword())) {
                config.useClusterServers()
                        .setPassword(distributionProperties.getRedis().getPassword())
                ;
            }
        }
    }

    private void updateSentinelServer(Config config) {
        if (null == distributionProperties.getRedis().getSentinel())
            return;

        String masterName;
        if (StringUtils.isNotBlank(masterName = distributionProperties.getRedis().getSentinel().getMasterName())) {
            config.useSentinelServers()
                    .addSentinelAddress(distributionProperties.getRedis().getAddress().toArray(new String[0]))
                    .setMasterName(masterName)
                    .setRetryAttempts(distributionProperties.getRetryAttempts())
                    .setDatabase(0);

            if (StringUtils.isNotBlank(distributionProperties.getRedis().getPassword())) {
                config.useSentinelServers()
                        .setPassword(distributionProperties.getRedis().getPassword())
                ;
            }
        }
    }

    private void updateSingleServer(Config config) {
        List<String> address;
        if (1 == (address = distributionProperties.getRedis().getAddress()).size()) {
            config.useSingleServer()
                    .setAddress(address.get(0)).setDatabase(0)
                    .setRetryAttempts(distributionProperties.getRetryAttempts())
            ;
            if (StringUtils.isNotBlank(distributionProperties.getRedis().getPassword())) {
                config.useSingleServer()
                        .setPassword(distributionProperties.getRedis().getPassword())
                ;
            }
        }
    }
}
