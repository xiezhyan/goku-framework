package top.zopx.starter.tools.tools.worker;

/**
 * 雪花ID
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public class Id {

    private volatile static Id INSTANCE = null;
    private SnowflakeWorker worker;

    private Id(long workerId, long datacenterId) {
        worker = new SnowflakeWorker(workerId, datacenterId);
    }

    public static Id getInstance(long workerId, long datacenterId) {
        if (null == INSTANCE) {
            synchronized (Id.class) {
                if (null == INSTANCE) {
                    INSTANCE = new Id(workerId, datacenterId);
                }
            }
        }
        return INSTANCE;
    }

    public Long getId() {
        return worker.nextId();
    }
}
