package top.zopx.goku.framework.tools.pass.codec.crc32;

import org.apache.commons.lang3.ArrayUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.zip.CRC32;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/08/14 13:58
 */
public enum CRC32Util {

    /**
     * 单例
     */
    INSTANCE,
    ;

    private static final CRC32 CRC_32 = new CRC32();

    /**
     * 编码
     *
     * @param joining 连接符
     * @param values  具体参数
     * @return CRC32编码结果
     */
    public long encode(String joining, String... values) {
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
