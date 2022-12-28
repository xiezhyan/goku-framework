package top.zopx.goku.framework.material.entity.vo;

import java.io.Serializable;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/31
 */
public class MaterialPreVO implements Serializable {

    /**
     *
     */
    private String accessid;

    /**
     *
     */
    private String policy;

    /**
     *
     */
    private String signature;

    /**
     *
     */
    private String dir;

    /**
     *
     */
    private String host;

    /**
     *
     */
    private String expire;

    public String getAccessid() {
        return accessid;
    }

    public void setAccessid(String accessid) {
        this.accessid = accessid;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }
}
