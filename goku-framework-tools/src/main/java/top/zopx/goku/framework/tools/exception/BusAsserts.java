package top.zopx.goku.framework.tools.exception;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/5/31 21:44
 */
public class BusAsserts {


    public static void isTrue(boolean isTrue, String msg, Object... format) {
        if (!isTrue) {
            throw new BusException(msg, IBus.ERROR_CODE, format);
        }
    }

    public static void isTrue(boolean isTrue,  IBus bus, Object... format) {
        if (!isTrue) {
            throw new BusException(bus, format);
        }
    }

    public static void isNull(Object obj, String msg, Object... format) {
        if (Objects.isNull(obj)) {
            throw new BusException(msg, IBus.ERROR_CODE, format);
        }
    }

    public static void isNull(Object obj, IBus bus, Object... format) {
        if (Objects.isNull(obj)) {
            throw new BusException(bus, format);
        }
    }

    public static void isBlank(String data, String msg, Object... format) {
        if (StringUtils.isBlank(data)) {
            throw new BusException(msg, IBus.ERROR_CODE, format);
        }
    }

    public static void isBlank(String data, IBus bus, Object... format) {
        if (StringUtils.isBlank(data)) {
            throw new BusException(bus, format);
        }
    }

    public static <T> void isEmpty(Collection<T> data, String msg, Object... format) {
        if (CollectionUtils.isEmpty(data)) {
            throw new BusException(msg, IBus.ERROR_CODE, format);
        }
    }

    public static <T> void isEmpty(Collection<T> data, IBus bus, Object... format) {
        if (CollectionUtils.isEmpty(data)) {
            throw new BusException(bus, format);
        }
    }

    public static <K,V> void isEmpty(Map<K,V> map, String msg, Object... format) {
        if (MapUtils.isEmpty(map)) {
            throw new BusException(msg, IBus.ERROR_CODE, format);
        }
    }

    public static <K,V> void isEmpty(Map<K,V> map, IBus bus, Object... format) {
        if (MapUtils.isEmpty(map)) {
            throw new BusException(bus, format);
        }
    }
}
