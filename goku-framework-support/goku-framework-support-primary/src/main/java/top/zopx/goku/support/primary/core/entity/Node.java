package top.zopx.goku.support.primary.core.entity;

/**
 * @author 俗世游子
 * @date 2022/1/24
 * @email xiezhyan@126.com
 */
public class Node {

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
