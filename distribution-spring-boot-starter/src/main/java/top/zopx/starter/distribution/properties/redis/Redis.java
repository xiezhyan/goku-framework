package top.zopx.starter.distribution.properties.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import top.zopx.starter.distribution.properties.DistributionProperties;

import java.util.List;

/**
 * @author sanq.Yan
 * @date 2021/3/28
 */
@Component
@Configuration
@ConfigurationProperties(Redis.PREFIX)
public class Redis {
    public static final String PREFIX = DistributionProperties.DISTRIBUTION_PREFIX + ".redis";

    private boolean open = false;

    private List<String> address;

    private String password;

    private Sentinel sentinel;

    public static class Sentinel {
        private String masterName;

        public String getMasterName() {
            return masterName;
        }

        public void setMasterName(String masterName) {
            this.masterName = masterName;
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
}
