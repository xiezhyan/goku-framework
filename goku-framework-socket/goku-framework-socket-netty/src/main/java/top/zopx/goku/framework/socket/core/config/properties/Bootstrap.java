package top.zopx.goku.framework.socket.core.config.properties;

import java.io.Serializable;
import java.util.Map;

/**
 * 单对象 默认从本地文件获取配置
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/25 20:07
 */
public class Bootstrap implements Serializable {
    /**
     * UKey
     */
    private BootstrapUKey ukey;

    /**
     * redis
     */
    private Map<String, BootstrapRedis> redis;

    /**
     * 数据库操作
     */
    private Map<String, BootstrapDatasource> datasource;

    public BootstrapUKey getUkey() {
        return ukey;
    }

    public void setUkey(BootstrapUKey ukey) {
        this.ukey = ukey;
    }

    public Map<String, BootstrapRedis> getRedis() {
        return redis;
    }

    public void setRedis(Map<String, BootstrapRedis> redis) {
        this.redis = redis;
    }

    public Map<String, BootstrapDatasource> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, BootstrapDatasource> datasource) {
        this.datasource = datasource;
    }
}
