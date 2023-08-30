package top.zopx.goku.framework.socket.netty.selector;

import org.apache.commons.collections4.CollectionUtils;
import top.zopx.goku.framework.socket.netty.entity.ClientProfile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/27
 */
public final class SelectorUtil {

    private SelectorUtil() {}

    private static final Map<Integer, ClientProfile> ID_SERVER_MAP = new ConcurrentHashMap<>(64);

    private static final List<ClientProfile> serverProfileList = new ArrayList<>(0);

    private static final AtomicLong REV = new AtomicLong(0);

    public static ClientProfile get(Integer serverId) {
        return ID_SERVER_MAP.get(serverId);
    }

    public static List<ClientProfile> get() {
        return get(Comparator.comparing(ClientProfile::getLoadCount));
    }

    public static List<ClientProfile> get(Comparator<ClientProfile> comparing) {
        if (CollectionUtils.isEmpty(serverProfileList)) {
            serverProfileList.addAll(ID_SERVER_MAP.values());
            serverProfileList.sort(comparing);
        }
        return serverProfileList;
    }


    public static void put(Integer serverId, ClientProfile clientProfile) {
        if (null == serverId || null == clientProfile) {
            return;
        }

        ID_SERVER_MAP.put(serverId, clientProfile);
    }

    public static void clean() {
        serverProfileList.clear();
    }

    public static long getRev() {
        return REV.get();
    }

    public static void setRev(long value) {
        REV.set(value);
    }

    public static ClientProfile remove(int serverId) {
        return ID_SERVER_MAP.remove(serverId);
    }
}
