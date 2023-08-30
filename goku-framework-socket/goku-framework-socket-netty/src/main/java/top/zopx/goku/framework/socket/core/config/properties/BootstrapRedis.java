package top.zopx.goku.framework.socket.core.config.properties;

import java.io.Serializable;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/25 08:50
 */
public class BootstrapRedis implements Serializable {
    private String host = "127.0.0.1";

    private Integer port = 6379;

    private String password;

    private Integer index = 8;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
