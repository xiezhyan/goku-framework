package top.zopx.goku.framework.tools.digest.sm2;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import top.zopx.goku.framework.tools.digest.base64.Base64Util;
import top.zopx.goku.framework.tools.digest.rsa.RsaKey;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:06
 */
@SuppressWarnings("all")
public class SM2Util {


    public static RsaKey generKey() {
        KeyPair keyGen = getKeyGen();
        if (Objects.isNull(keyGen)) {
            throw new RuntimeException("");
        }

        return new RsaKey(
                Base64Util.INSTANCE.encode(keyGen.getPublic().getEncoded()),
                Base64Util.INSTANCE.encode(keyGen.getPrivate().getEncoded())
        );
    }

    public static String encrypt(String data, String publicKeyStr) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            // 获取椭圆曲线KEY生成器
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            byte[] publicKeyData = Base64.decodeBase64(publicKeyStr);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyData);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            CipherParameters publicKeyParamerters = ECUtil.generatePublicKeyParameter(publicKey);
            //数据加密
            SM2Engine engine = new SM2Engine(new SM3Digest(), SM2Engine.Mode.C1C3C2);
            engine.init(true, new ParametersWithRandom(publicKeyParamerters));
            byte[] encryptData = engine.processBlock(data.getBytes(), 0, data.getBytes().length);
            return Base64.encodeBase64String(encryptData);
        } catch (Exception e) {
            return "";
        }
    }

    public static String decrypt(String encrypt, String privateKeyStr) {
        Security.addProvider(new BouncyCastleProvider());
        try {
            // 获取椭圆曲线KEY生成器
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            byte[] privateKeyData = Base64.decodeBase64(privateKeyStr);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyData);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            CipherParameters privateKeyParamerters = ECUtil.generatePrivateKeyParameter(privateKey);
            //数据解密
            SM2Engine engine = new SM2Engine(new SM3Digest(), SM2Engine.Mode.C1C3C2);
            engine.init(false, privateKeyParamerters);
            byte[] decryptData = Base64.decodeBase64(encrypt);
            byte[] plainText = engine.processBlock(decryptData, 0, decryptData.length);
            return new String(plainText);
        } catch (Exception e) {
            return "";
        }
    }

    private static KeyPair getKeyGen() {
        try {
            final ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");
            // 获取一个椭圆曲线类型的密钥对生成器
            final KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
            SecureRandom random = new SecureRandom();
            // 使用SM2的算法区域初始化密钥生成器
            kpg.initialize(sm2Spec, random);

            KeyPair keyPair = kpg.generateKeyPair();
            return keyPair;
        } catch (Exception e) {
            return null;
        }
    }

}
