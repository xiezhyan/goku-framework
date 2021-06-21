package top.zopx.starter.tools.digest.rsa;

import top.zopx.starter.tools.digest.base64.Base64Util;
import top.zopx.starter.tools.exceptions.BusException;
import top.zopx.starter.tools.tools.web.LogUtil;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA 对称加密
 *
 * @author sanq.Yan
 * @date 2021/5/11
 */
public enum RSAUtil {

    /**
     * 单例
     */
    INSTANCE,
    ;

    private static final String SECRET_KEY_SPEC = "AES";
    private static final String PADDING = "AES/CBC/PKCS5Padding";

    private static final String PUBLIC_KEY = "_PUBLIC_KEY";
    private static final String PRIVATE_KEY = "_PRIVATE_KEY";

    public Map<String, String> genKeyPair() {

        try {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(1024, new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 得到公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            String publicKeyString = Base64Util.INSTANCE.encode(publicKey.getEncoded());
            // 得到私钥字符串
            String privateKeyString = Base64Util.INSTANCE.encode(privateKey.getEncoded());
            // 将公钥和私钥保存到Map
            return new HashMap<String, String>(4){{
                put(PUBLIC_KEY, publicKeyString);
                put(PRIVATE_KEY, privateKeyString);
            }};
        } catch (Exception e) {
            LogUtil.getInstance(RSAUtil.class).error(e.getMessage());
            throw new BusException(e.getMessage());
        }
    }

}
