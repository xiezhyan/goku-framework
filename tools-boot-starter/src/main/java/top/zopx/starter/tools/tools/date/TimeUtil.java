package top.zopx.starter.tools.tools.date;

import org.apache.commons.lang3.RandomUtils;
import top.zopx.starter.tools.tools.strings.StringUtil;

import java.time.LocalTime;

/**
 * com.sanq.product.cab.utils.TimeUtil
 *
 * @author sanq.Yan
 * @date 2020/3/11
 */
public class TimeUtil {

    public static long getTime(double time) {
        return StringUtil.toLong((time + RandomUtils.nextLong(0, 180)));
    }

    public static long to24() {
        return getTime(LocalDateUtils.to24());
    }
}
