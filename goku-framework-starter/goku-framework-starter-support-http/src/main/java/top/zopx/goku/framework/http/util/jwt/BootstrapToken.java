package top.zopx.goku.framework.http.util.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static top.zopx.goku.framework.http.util.jwt.BootstrapToken.PREFIX;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/5/27 09:40
 */
@Component
@Configuration
@ConfigurationProperties(PREFIX)
public class BootstrapToken {
    public static final String PREFIX = "goku.jwt";

    /**
     * 主题
     */
    private String subject = "goku.jwt.auth.subject";
    /**
     * 发行者
     */
    private String issue = "goku.framework";
    /**
     * token到期时间
     */
    private Duration expireTime = Duration.ofHours(2);

    /**
     * 刷新token到期时间
     */
    private Duration refreshExpireTime = Duration.ofDays(30);
    /**
     * 加密公钥
     */
    private String publicKey;
    /**
     * 加密私钥
     */
    private String privateKey;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Duration getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Duration expireTime) {
        this.expireTime = expireTime;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Duration getRefreshExpireTime() {
        return refreshExpireTime;
    }

    public void setRefreshExpireTime(Duration refreshExpireTime) {
        this.refreshExpireTime = refreshExpireTime;
    }
}
