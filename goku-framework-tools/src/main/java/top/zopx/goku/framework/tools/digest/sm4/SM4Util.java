package top.zopx.goku.framework.tools.digest.sm4;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.tools.exceptions.IBus;

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
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:06
 */
public class SM4Util {

    public static final String ENCODING = "UTF-8";
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
            throw new BusException(e.getMessage(), IBus.ERROR_CODE, e.getMessage());
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
            throw new BusException(e.getMessage(), IBus.ERROR_CODE, e.getMessage());
        }
    }

    /**
     * 加密
     *
     * @param key     key
     * @param data    需要加密数据
     * @param charset 编码
     * @return 加密后数据
     */
    public static String encrypt(String key, String data, String charset) {
        String cipherText = "";
        if (null != data && !"".equals(data)) {
            byte[] keyData = ByteUtils.fromHexString(key);
            charset = charset.trim();
            if (charset.length() <= 0) {
                charset = ENCODING;
            }

            try {
                byte[] srcData = data.getBytes(charset);
                byte[] cipherArray = encryptEcbPadding(keyData, srcData);
                cipherText = ByteUtils.toHexString(cipherArray);
            } catch (Exception e) {
                throw new BusException(e.getMessage(), IBus.ERROR_CODE, e.getMessage());
            }
        }
        return cipherText;
    }

    private static byte[] encryptEcbPadding(byte[] key, byte[] data) {
        try {
            Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE, e.getMessage());
        }
    }

    /**
     * 解码
     *
     * @param key     key
     * @param data    解码数据
     * @param charset 编码
     * @return 返回原数据
     * @throws Exception 异常
     */
    public static String decrypt(String key, String data, String charset) {
        String decryptStr = "";
        byte[] keyData = ByteUtils.fromHexString(key);
        byte[] cipherData = ByteUtils.fromHexString(data);
        try {
            byte[] srcData = decryptEcbPadding(keyData, cipherData);
            charset = charset.trim();
            if (charset.length() <= 0) {
                charset = ENCODING;
            }
            decryptStr = new String(srcData, charset);
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE, e.getMessage());
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
            throw new BusException(e.getMessage(), IBus.ERROR_CODE, e.getMessage());
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
            throw new BusException(e.getMessage(), IBus.ERROR_CODE, e.getMessage());
        }
    }
}