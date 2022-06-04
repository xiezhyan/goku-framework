package top.zopx.goku.framework.primary.core.event;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 俗世游子
 * @date 2022/1/17
 * @email xiezhyan@126.com
 */
public class FillingSegmentUpdater {

    private static final EventBus EVENT_BUS = new AsyncEventBus(
            "Segment_Filling_Update_BUS",
            new ThreadPoolExecutor(
                    5,
                    10,
                    60,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy()
            )
    );

    public FillingSegmentUpdater() {
        EVENT_BUS.register(new FillingSegmentListener());
    }

    public void startFilling(FillingSegmentEvent event) {
        EVENT_BUS.post(event);
    }
}
