package top.zopx.goku.framework.tools.digest.rsa;


import top.zopx.goku.framework.tools.digest.base64.Base64Util;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.tools.exceptions.IBus;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA 对称加密
 *
 * @author 俗世游子
 * @date 2021/5/11
 */
public enum RSAUtil {

    /**
     * 单例
     */
    INSTANCE,
    ;

    private static final String SECRET_KEY_SPEC_RSA = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";
    private static final String RSA = "RSA";

    /**
     * 生成公钥和私钥
     *
     * @return RsaKey
     */
    public RsaKey genKeyPair() {
        try {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);
            // 初始化密钥对生成器
            keyPairGen.initialize(2048, new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 得到公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 将公钥和私钥保存   得到私钥字符串
            return new RsaKey(Base64Util.INSTANCE.encode(publicKey.getEncoded()), Base64Util.INSTANCE.encode(privateKey.getEncoded()));
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE, e.getMessage());
        }
    }

    /**
     * 公钥加密
     *
     * @return 加密之后的数据
     */
    public String encrypt(String text, String publicKey) {
        //base64编码的公钥
        byte[] decoded = Base64Util.INSTANCE.decode(publicKey);
        RSAPublicKey pubKey = getPublicKey(decoded);
        try {
            //RSA加密
            Cipher cipher = Cipher.getInstance(SECRET_KEY_SPEC_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64Util.INSTANCE.encode(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE, "");
        }
    }

    /**
     * 私钥解密
     *
     * @return 解密之后的数据，需要通过base64进行转码
     */
    public String decrypt(String text, String privateKey) {
        //64位解码加密后的字符串
        byte[] inputByte = Base64Util.INSTANCE.decode(text);
        //base64编码的私钥
        byte[] decoded = Base64Util.INSTANCE.decode(privateKey);
        RSAPrivateKey priKey = getPrivateKey(decoded);
        try {
            //RSA解密
            Cipher cipher = Cipher.getInstance(SECRET_KEY_SPEC_RSA);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            return Base64Util.INSTANCE.encode(cipher.doFinal(inputByte));
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE, "");
        }
    }

    /**
     * 得到RSAPublicKey
     *
     * @param bytes 公钥字符串  Base64Util.INSTANCE.decode(publicKey);
     * @return RSAPublicKey
     * @throws Exception 转换异常
     */
    public RSAPublicKey getPublicKey(byte[] bytes) {
        try {
            return (RSAPublicKey) KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(bytes));
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE, e.getMessage());
        }
    }

    /**
     * 得到RSAPrivateKey
     *
     * @param bytes 私钥字符串 Base64Util.INSTANCE.decode(privateKey);
     * @return RSAPrivateKey
     * @throws Exception 转换异常
     */
    public RSAPrivateKey getPrivateKey(byte[] bytes) {
        try {
            return (RSAPrivateKey) KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(bytes));
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE, e.getMessage());
        }
    }
}
