package top.zopx.goku.framework.tools.pass.crtpt;

import java.io.Serializable;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/5/9
 */
public class GenericKey implements Serializable {

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    public GenericKey() {
    }

    public GenericKey(String publicKey, String privateKey) {
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
