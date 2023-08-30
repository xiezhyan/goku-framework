package top.zopx.goku.framework.tools.pass.crtpt.sm2;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import top.zopx.goku.framework.tools.pass.codec.base64.Base64Util;
import top.zopx.goku.framework.tools.pass.crtpt.GenericKey;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 椭圆曲线公钥密码算法
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:06
 */
public class SM2Util {

    private SM2Util() {
    }

    public static GenericKey createKeys() {
        KeyPair keyGen = getKeyGen();
        return new GenericKey(
                Base64Util.INSTANCE.encode(keyGen.getPublic().getEncoded()),
                Base64Util.INSTANCE.encode(keyGen.getPrivate().getEncoded())
        );
    }

    /**
     * 编码
     *
     * @param data         待编码字符串
     * @param publicKeyStr 公钥
     * @return Base64字符串
     */
    public static String encrypt(String data, String publicKeyStr) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            // 获取椭圆曲线KEY生成器
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            byte[] publicKeyData = Base64Util.INSTANCE.decode(publicKeyStr);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyData);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            CipherParameters publicKeyParamerters = ECUtil.generatePublicKeyParameter(publicKey);
            //数据加密
            SM2Engine engine = new SM2Engine(new SM3Digest(), SM2Engine.Mode.C1C3C2);
            engine.init(true, new ParametersWithRandom(publicKeyParamerters));
            byte[] encryptData = engine.processBlock(data.getBytes(), 0, data.getBytes().length);
            return Base64Util.INSTANCE.encode(encryptData);
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }

    /**
     * 解码
     *
     * @param encrypt       待解码字符串
     * @param privateKeyStr 私钥
     * @return Base64字符串
     */
    public static String decrypt(String encrypt, String privateKeyStr) {
        Security.addProvider(new BouncyCastleProvider());
        try {
            // 获取椭圆曲线KEY生成器
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            byte[] privateKeyData = Base64Util.INSTANCE.decode(privateKeyStr);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyData);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            CipherParameters privateKeyParamerters = ECUtil.generatePrivateKeyParameter(privateKey);
            //数据解密
            SM2Engine engine = new SM2Engine(new SM3Digest(), SM2Engine.Mode.C1C3C2);
            engine.init(false, privateKeyParamerters);
            byte[] decryptData = Base64Util.INSTANCE.decode(encrypt);
            byte[] plainText = engine.processBlock(decryptData, 0, decryptData.length);
            return Base64Util.INSTANCE.encode(plainText);
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
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
            return kpg.generateKeyPair();
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }
}
