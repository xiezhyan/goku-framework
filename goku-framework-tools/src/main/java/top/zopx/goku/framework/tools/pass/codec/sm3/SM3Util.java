package top.zopx.goku.framework.tools.pass.codec.sm3;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.legacy.math.linearalgebra.ByteUtils;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.nio.charset.StandardCharsets;
import java.security.Security;

/**
 * 杂凑算法
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:06
 */
@SuppressWarnings("all")
public final class SM3Util {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private SM3Util() {
    }

    /**
     * 加密
     *
     * @param data 需要加密的参数
     * @return 加密后的结果
     */
    public static String digest(String data) {
        // 将返回的hash值转换成16进制字符串
        String resultHex = "";
        try {
            // 将字符串转换成byte数组
            byte[] srcData = data.getBytes(StandardCharsets.UTF_8);
            // 调用hash()
            byte[] resultHash = hash(srcData);
            // 将返回的hash值转换成16进制字符串
            resultHex = ByteUtils.toHexString(resultHash).toUpperCase();
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
        return resultHex;
    }

    /**
     * 校验是否一致
     *
     * @param encrypt 加密之后的结果
     * @param data    源
     * @return 是否一致
     */
    public static boolean check(String encrypt, String data) {
        return StringUtils.equals(encrypt, data.toUpperCase());
    }

    private static byte[] hash(byte[] srcData) {
        SM3Digest digest = new SM3Digest();
        //update the message digest with a single byte.
        digest.update(srcData, 0, srcData.length);
        byte[] hash = new byte[digest.getDigestSize()];
        //close the digest, producing the final digest value.
        digest.doFinal(hash, 0);
        return hash;
    }
}
