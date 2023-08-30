package top.zopx.goku.framework.support.primary.core.entity;

import java.io.Serializable;

/**
 * @author Mr.Xie
 * @date 2022/1/24
 * @email xiezhyan@126.com
 */
public class Node implements Serializable {

    /**
     * 节点IP
     */
    private String ip;

    /**
     * 占用端口
     */
    private Integer port;

    /**
     * 时间戳
     */
    private Long currentTime = System.currentTimeMillis();

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }
}
