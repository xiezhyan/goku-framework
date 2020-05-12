package top.zopx.starter.upload.util;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * top.zopx.starter.upload.util.Dir
 *
 * @author sanq.Yan
 * @date 2020/5/12
 */
public class Dir {

    private final static ThreadLocal<String> THREAD_LOCAL = ThreadLocal.withInitial(() -> {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return simpleDateFormat.format(new Date());
    });

    public static String get () {
        return THREAD_LOCAL.get();
    }
}
