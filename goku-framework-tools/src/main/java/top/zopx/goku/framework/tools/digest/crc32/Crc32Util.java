package top.zopx.goku.framework.tools.digest.crc32;

import org.apache.commons.lang3.ArrayUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.zip.CRC32;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/08/14 13:58
 */
public enum Crc32Util {

    /**
     * 单例
     */
    INSTANCE,
    ;

    private static final CRC32 CRC_32 = new CRC32();

    public long encode(String joining, String...values) {
        if (ArrayUtils.isEmpty(values)) {
            return -1L;
        }
        // 每次进行重置
        CRC_32.reset();
        final StringJoiner joiner = new StringJoiner(joining);
        Arrays.stream(values).forEach(joiner::add);
        CRC_32.update(joiner.toString().getBytes(StandardCharsets.UTF_8));
        return CRC_32.getValue();
    }
}
