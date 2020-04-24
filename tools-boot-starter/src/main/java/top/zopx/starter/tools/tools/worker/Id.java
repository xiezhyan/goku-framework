package top.zopx.starter.tools.tools.worker;

/**
 * version: 生成ID
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public class Id {

    private volatile static Id INSTANCE = null;
    private SnowflakeWorker WORKER;

    private Id(long workerId, long datacenterId) {
        WORKER = new SnowflakeWorker(workerId, datacenterId);
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
        return WORKER.nextId();
    }
}
