package top.zopx.starter.tools.digest.rsa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.starter.tools.digest.base64.Base64Util;
import top.zopx.starter.tools.exceptions.BusException;

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

    private static final String SECRET_KEY_SPEC_RSA = "RSA";
    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtil.class);

    /**
     * 生成公钥和私钥
     * @return RsaKey
     */
    public RsaKey genKeyPair() {
        try {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(SECRET_KEY_SPEC_RSA);
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
            // 将公钥和私钥保存
            return new RsaKey(publicKeyString, privateKeyString);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BusException(e.getMessage());
        }
    }

    /**
     * 公钥加密
     * @return 加密之后的数据
     */
    public String encrypt(String text, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64Util.INSTANCE.decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(SECRET_KEY_SPEC_RSA).generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance(SECRET_KEY_SPEC_RSA);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64Util.INSTANCE.encode(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 私钥解密
     * @return 解密之后的数据，需要通过base64进行转码
     */
    public String decrypt(String text, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64Util.INSTANCE.decode(text);
        //base64编码的私钥
        byte[] decoded = Base64Util.INSTANCE.decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(SECRET_KEY_SPEC_RSA).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance(SECRET_KEY_SPEC_RSA);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return Base64Util.INSTANCE.encode(cipher.doFinal(inputByte));
    }
}
