package top.zopx.goku.framework.socket.discovery.configure;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 13:18
 */
public class JedisConfigure implements Serializable {

    @SerializedName("serverHost")
    private String host = "127.0.0.1";

    @SerializedName("serverPort")
    private Integer port = 6379;

    private String password;

    private Integer index = 14;

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
