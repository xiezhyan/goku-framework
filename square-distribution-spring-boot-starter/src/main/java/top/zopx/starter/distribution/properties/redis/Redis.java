package top.zopx.starter.distribution.properties.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import top.zopx.starter.distribution.properties.SquareDistributionProperties;

import java.util.List;

/**
 * @author sanq.Yan
 * @date 2021/3/28
 */
@Component
@Configuration
@ConfigurationProperties(Redis.PREFIX)
public class Redis {
    public static final String PREFIX = SquareDistributionProperties.DISTRIBUTION_PREFIX + ".redis";


    /**
     * redis 集群扫描间隔
     */
    private int interval = 3000;

    /**
     * 是否启动
     */
    private boolean open = false;

    /**
     * redis地址,协议：redis://
     */
    private List<String> address;

    /**
     * 密码
     */
    private String password;

    /**
     * 哨兵配置
     */
    private Sentinel sentinel;

    /**
     * 主从
     */
    private MasterSlave masterSlave;

    public static class Sentinel {
        private String masterName;

        public String getMasterName() {
            return masterName;
        }

        public void setMasterName(String masterName) {
            this.masterName = masterName;
        }
    }

    public static class MasterSlave {
        public List<String> slaves;

        public List<String> getSlaves() {
            return slaves;
        }

        public void setSlaves(List<String> slaves) {
            this.slaves = slaves;
        }
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public Sentinel getSentinel() {
        return sentinel;
    }

    public void setSentinel(Sentinel sentinel) {
        this.sentinel = sentinel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public MasterSlave getMasterSlave() {
        return masterSlave;
    }

    public void setMasterSlave(MasterSlave masterSlave) {
        this.masterSlave = masterSlave;
    }
}
