package top.zopx.goku.framework.socket.core.config.properties;

import java.io.Serializable;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/25 21:48
 */
public class BootstrapUKey implements Serializable {

    /**
     * 密码
     */
    private String password;

    /**
     * 有效期： 单位s
     */
    private Long ttl;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }
}
