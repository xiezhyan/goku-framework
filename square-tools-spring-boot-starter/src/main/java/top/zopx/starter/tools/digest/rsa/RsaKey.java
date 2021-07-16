package top.zopx.starter.tools.digest.rsa;

import java.io.Serializable;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/7/16
 */
public class RsaKey implements Serializable {

    private String publicKey;

    private String privateKey;

    public RsaKey() {
    }

    public RsaKey(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
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
}
