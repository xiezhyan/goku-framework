package top.zopx.starter.distribution.configurator;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import top.zopx.starter.distribution.properties.SquareDistributionProperties;
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
    private SquareDistributionProperties squareDistributionProperties;

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
        if (null != squareDistributionProperties.getRedis().getMasterSlave()) {
            final MasterSlaveServersConfig masterSlaveServersConfig = config.useMasterSlaveServers()
                    .setDatabase(0)
                    .setRetryAttempts(squareDistributionProperties.getRetryAttempts())
                    .setRetryInterval(squareDistributionProperties.getRedis().getInterval())
                    .setMasterAddress(squareDistributionProperties.getRedis().getAddress().get(0))
                    .addSlaveAddress(squareDistributionProperties.getRedis().getMasterSlave().getSlaves().toArray(new String[0]));

            if (StringUtils.isNotBlank(squareDistributionProperties.getRedis().getPassword())) {
                masterSlaveServersConfig
                        .setPassword(squareDistributionProperties.getRedis().getPassword());
            }
        }
    }

    private void updateClusterServer(Config config) {
        List<String> address;
        if (1 < (address = squareDistributionProperties.getRedis().getAddress()).size()) {
            final ClusterServersConfig clusterServersConfig = config.useClusterServers()
                    .setScanInterval(squareDistributionProperties.getRedis().getInterval())
                    .addNodeAddress(address.toArray(new String[0]))
                    .setRetryAttempts(squareDistributionProperties.getRetryAttempts());

            if (StringUtils.isNotBlank(squareDistributionProperties.getRedis().getPassword())) {
                clusterServersConfig
                        .setPassword(squareDistributionProperties.getRedis().getPassword())
                ;
            }
        }
    }

    private void updateSentinelServer(Config config) {
        if (null == squareDistributionProperties.getRedis().getSentinel()) {
            return;
        }

        String masterName;
        if (StringUtils.isNotBlank(masterName = squareDistributionProperties.getRedis().getSentinel().getSentinelName())) {
            final SentinelServersConfig sentinelServersConfig = config.useSentinelServers()
                    .addSentinelAddress(squareDistributionProperties.getRedis().getAddress().toArray(new String[0]))
                    .setMasterName(masterName)
                    .setRetryAttempts(squareDistributionProperties.getRetryAttempts())
                    .setDatabase(0);

            if (StringUtils.isNotBlank(squareDistributionProperties.getRedis().getPassword())) {
                sentinelServersConfig
                        .setPassword(squareDistributionProperties.getRedis().getPassword())
                ;
            }
        }
    }

    private void updateSingleServer(Config config) {
        List<String> address;
        if (1 == (address = squareDistributionProperties.getRedis().getAddress()).size()) {
            final SingleServerConfig singleServerConfig = config.useSingleServer()
                    .setAddress(address.get(0)).setDatabase(0)
                    .setRetryAttempts(squareDistributionProperties.getRetryAttempts());

            if (StringUtils.isNotBlank(squareDistributionProperties.getRedis().getPassword())) {
                singleServerConfig
                        .setPassword(squareDistributionProperties.getRedis().getPassword())
                ;
            }
        }
    }
}
