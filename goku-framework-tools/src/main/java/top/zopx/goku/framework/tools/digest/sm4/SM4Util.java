package top.zopx.goku.framework.tools.digest.sm4;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

/**
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
    private static Cipher generateEcbCipher(String algorithmName, int mode, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORIGTHM_NAME);
        cipher.init(mode, sm4Key);
        return cipher;
    }

    /**
     * 自动生成密钥
     *
     * @return 秘钥
     */
    public static String generateKey() {
        try {
            return generateKey(DEFAULT_KEY_SIZE);
        } catch (Exception e) {
            return "";
        }
    }

    public static String generateKey(int keySize) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(ALGORIGTHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
            kg.init(keySize, new SecureRandom());
            return ByteUtils.toHexString(kg.generateKey().getEncoded());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 加密
     *
     * @param key     key
     * @param data    需要加密数据
     * @param charset 编码
     * @return 加密后数据
     * @throws Exception 异常信息
     */
    public static String encrypt(String key, String data, String charset) throws Exception {
        String cipherText = "";
        if (null != data && !"".equals(data)) {
            byte[] keyData = ByteUtils.fromHexString(key);
            charset = charset.trim();
            if (charset.length() <= 0) {
                charset = ENCODING;
            }
            byte[] srcData = data.getBytes(charset);
            byte[] cipherArray = encryptEcbPadding(keyData, srcData);
            cipherText = ByteUtils.toHexString(cipherArray);
        }
        return cipherText;
    }

    private static byte[] encryptEcbPadding(byte[] key, byte[] data) throws Exception {
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
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
    public static String decrypt(String key, String data, String charset) throws Exception {
        String decryptStr = "";
        byte[] keyData = ByteUtils.fromHexString(key);
        byte[] cipherData = ByteUtils.fromHexString(data);
        byte[] srcData = decryptEcbPadding(keyData, cipherData);
        charset = charset.trim();
        if (charset.length() <= 0) {
            charset = ENCODING;
        }
        decryptStr = new String(srcData, charset);
        return decryptStr;
    }

    /**
     * @Description:解密
     */
    private static byte[] decryptEcbPadding(byte[] key, byte[] cipherText) throws Exception {
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    public static boolean verifyEcb(String hexKey, String cipherText, String paramStr) throws Exception {
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        byte[] cipherData = ByteUtils.fromHexString(cipherText);
        byte[] decryptData = decryptEcbPadding(keyData, cipherData);
        byte[] srcData = paramStr.getBytes(StandardCharsets.UTF_8);
        return Arrays.equals(decryptData, srcData);
    }
}