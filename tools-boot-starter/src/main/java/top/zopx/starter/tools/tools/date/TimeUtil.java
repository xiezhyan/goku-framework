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

    public static int getTime(double time) {
        return StringUtil.toInteger((time + RandomUtils.nextInt(0, 180)));
    }

    public static int to24() {
        return LocalDateUtils.to24() + RandomUtils.nextInt(0, 180);
    }
}
