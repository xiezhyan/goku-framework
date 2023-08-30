package top.zopx.goku.framework.redis.lock.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import static top.zopx.goku.framework.redis.lock.properties.BootstrapLock.BOOTSTRAP_PREFIX;
/**
 * 锁配置
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/23 7:38
 */
@Configuration
@ConfigurationProperties(BOOTSTRAP_PREFIX)
public class BootstrapLock implements Serializable {
    public static final String BOOTSTRAP_PREFIX = "spring.data.redis.lock";

    /**
     * 前缀
     */
    private String prefix = "lock";

    /**
     * 加锁后自动释放时间（秒），默认自动续期
     */
    private Integer expireTime = -1;
    /**
     * 加锁的最长等待时间，超过则抛出异常（秒）
     */
    private Integer maxWaitTime = 2;

    private LockEnum lockType = LockEnum.REENTRANT_LOCK;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(Integer maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public LockEnum getLockType() {
        return lockType;
    }

    public void setLockType(LockEnum lockType) {
        this.lockType = lockType;
    }

    public enum LockEnum {
        /**
         * 可重入锁
         */
        REENTRANT_LOCK,
        /**
         * 公平锁
         */
        FAIR_LOCK,
        /**
         * 自旋锁
         */
        SPIN_LOCK,
        ;
    }
}
