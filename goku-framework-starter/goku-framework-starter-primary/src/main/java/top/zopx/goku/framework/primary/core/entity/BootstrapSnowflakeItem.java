package top.zopx.goku.framework.primary.core.entity;

/**
 * @author 俗世游子
 * @date 2022/1/24
 * @email xiezhyan@126.com
 */
public class BootstrapSnowflakeItem {

    /**
     * 服务IP
     */
    private String host;

    /**
     * 服务端口
     */
    private Integer port;

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
}
