package top.zopx.goku.framework.socket.core.selector;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 09:58
 */
@SuppressWarnings("all")
public final class Selector {

    private static final Map<Integer, ClientProfile> ID_SERVER_MAP = new ConcurrentHashMap<>(64);
    private static final List<ClientProfile> INSTANCE_LIST = new ArrayList<>(0);
    private static final AtomicLong REV = new AtomicLong(0);

    private Selector() {
    }

    private static class Holder {
         private static final Selector INSTANCE = new Selector();
    }

    public static Selector getInstance() {
        return Holder.INSTANCE;
    }


    public Long getRev() {
        return REV.get();
    }

    public List<ClientProfile> list() {
        if (CollectionUtils.isEmpty(INSTANCE_LIST)) {
            INSTANCE_LIST.addAll(ID_SERVER_MAP.values());
        }
        return INSTANCE_LIST;
    }

    public void putInstance(Integer serverId, ClientProfile clientProfile) {
        if (null == serverId || null == clientProfile) {
            return;
        }

        INSTANCE_LIST.clear();
        ID_SERVER_MAP.put(serverId, clientProfile);
        REV.set(System.currentTimeMillis());
    }


    public ClientProfile removeById(Integer serverId) {
        if (null == serverId) {
            return null;
        }

        REV.set(System.currentTimeMillis());
        INSTANCE_LIST.clear();
        return ID_SERVER_MAP.remove(serverId);
    }

    public ClientProfile getInstanceById(Integer serverId) {
        if (null == serverId) {
            return null;
        }

        return ID_SERVER_MAP.get(serverId);
    }
}
