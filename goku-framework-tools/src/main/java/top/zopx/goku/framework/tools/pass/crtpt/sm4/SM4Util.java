package top.zopx.goku.framework.tools.pass.crtpt.sm4;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.legacy.math.linearalgebra.ByteUtils;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

/**
 * 对称算法
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:06
 */
@SuppressWarnings("all")
public class SM4Util {

    private static final String ALGORIGTHM_NAME = "SM4";
    private static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS7Padding";
    private static final int DEFAULT_KEY_SIZE = 128;

    private SM4Util() {
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 生成ecb暗号
     */
    private static Cipher generateEcbCipher(String algorithmName, int mode, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
            Key sm4Key = new SecretKeySpec(key, ALGORIGTHM_NAME);
            cipher.init(mode, sm4Key);
            return cipher;
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }

    /**
     * 自动生成密钥
     *
     * @return 秘钥
     */
    public static String createKeys() {
        return createKeys(DEFAULT_KEY_SIZE);
    }

    public static String createKeys(int keySize) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(ALGORIGTHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
            kg.init(keySize, new SecureRandom());
            return ByteUtils.toHexString(kg.generateKey().getEncoded());
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }

    /**
     * 编码
     *
     * @param key  key
     * @param data 需要加密数据
     * @return 加密后数据
     */
    public static String encrypt(String data, String key) {
        String cipherText = "";
        if (null != data && !"".equals(data)) {
            byte[] keyData = ByteUtils.fromHexString(key);

            try {
                byte[] srcData = data.getBytes(StandardCharsets.UTF_8);
                byte[] cipherArray = encryptEcbPadding(keyData, srcData);
                cipherText = ByteUtils.toHexString(cipherArray);
            } catch (Exception e) {
                throw new BusException(e.getMessage(), IBus.ERROR_CODE);
            }
        }
        return cipherText;
    }

    private static byte[] encryptEcbPadding(byte[] key, byte[] data) {
        try {
            Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }

    /**
     * 解码
     *
     * @param key  key
     * @param data 解码数据
     * @return 返回原数据
     */
    public static String decrypt(String data, String key) {
        String decryptStr = "";
        byte[] keyData = ByteUtils.fromHexString(key);
        byte[] cipherData = ByteUtils.fromHexString(data);
        try {
            byte[] srcData = decryptEcbPadding(keyData, cipherData);
            decryptStr = new String(srcData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
        return decryptStr;
    }

    /**
     *
     */
    private static byte[] decryptEcbPadding(byte[] key, byte[] cipherText) {
        try {
            Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(cipherText);
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }

    public static boolean check(String hexKey, String cipherText, String paramStr) {
        try {
            byte[] keyData = ByteUtils.fromHexString(hexKey);
            byte[] cipherData = ByteUtils.fromHexString(cipherText);
            byte[] decryptData = decryptEcbPadding(keyData, cipherData);
            byte[] srcData = paramStr.getBytes(StandardCharsets.UTF_8);
            return Arrays.equals(decryptData, srcData);
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }
}