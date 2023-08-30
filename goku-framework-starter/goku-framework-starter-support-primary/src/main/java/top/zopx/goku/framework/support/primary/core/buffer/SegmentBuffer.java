package top.zopx.goku.framework.support.primary.core.buffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.support.primary.core.entity.IDGetter;
import top.zopx.goku.framework.support.primary.core.event.FillingSegmentEvent;
import top.zopx.goku.framework.support.primary.core.event.FillingSegmentUpdater;
import top.zopx.goku.framework.support.primary.core.service.IBusinessService;
import top.zopx.goku.framework.support.primary.core.service.IUpdatePlot;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 双缓冲区
 *
 * @author Mr.Xie
 * @date 2022/1/17
 * @email xiezhyan@126.com
 */
@SuppressWarnings("all")
public class SegmentBuffer {
    private static final Logger LOG = LoggerFactory.getLogger(SegmentBuffer.class);
    /**
     * 初始状态
     */
    private static final int NORMAL = 0;
    /**
     * 下一个号段填充中
     */
    private static final int FILLING_NEXT_STATE = 1;
    /**
     * 下一个号段填充完成
     */
    private static final int FILLED_NEXT_STATE = 2;
    /**
     * 当前号段Segment已经改变
     */
    private static final int CHANGE_NEXT_STATE = 3;

    /**
     * 当前状态
     */
    private final AtomicInteger currentState = new AtomicInteger(NORMAL);

    /**
     * 当前正在使用的Segment
     */
    private Segment currentSegment;
    /**
     * 下一个Segment
     */
    private Segment nextSegment;

    /**
     * 绑定key
     */
    private String tag;

    private IUpdatePlot updatePlot;

    private IBusinessService businessService;

    private Exception e;

    private FillingSegmentUpdater fillingUpdater;

    public SegmentBuffer() {
    }

    public SegmentBuffer(String tag, IUpdatePlot updatePlot, IBusinessService businessService) {
        this.updatePlot = updatePlot;
        this.tag = tag;
        this.businessService = businessService;
        init();
    }

    public IDGetter next(int num) {
        checkException();
//        checkSegment();
        IDGetter idGetter;
        for (; ; ) {
            idGetter = currentSegment.next(num);
            if (null == idGetter) {
                checkSegment();

                for (; ; ) {
                    checkException();

                    if (this.currentState.get() == NORMAL) {
                        break;
                    }

                    if (currentState.get() == FILLED_NEXT_STATE
                            && null != nextSegment
                            && compareAndSwapCurrentState(currentState.get(), CHANGE_NEXT_STATE)) {
                        changeCurrentSegmentAndNotifyAll();
                        break;
                    }

                    synchronized (this) {
                        try {
                            this.wait();
                        } catch (InterruptedException ex) {
                            LOG.error("wait异常：{}", ex.getMessage());
                        }
                    }
                }
            } else {
                break;
            }
        }
        return idGetter;
    }

    /**
     * currentSegment切换之后，唤醒全部线程获取ID
     */
    private void changeCurrentSegmentAndNotifyAll() {
        this.currentSegment = nextSegment;
        this.nextSegment = null;
        this.currentState.getAndSet(NORMAL);
        synchronized (this) {
            this.notifyAll();
        }
    }

    /**
     * 验证是否可以填充下一块Segment
     */
    private void checkSegment() {
        if (currentSegment.percent().compareTo(updatePlot.percent(tag)) >= 0
                && null == nextSegment
                && compareAndSwapCurrentState(currentState.get(), FILLING_NEXT_STATE)) {
            LOG.info("开始填充:{}", tag);
            fillingUpdater.startFilling(new FillingSegmentEvent(this, businessService, tag, updatePlot.segmentPullSize(tag)));
        }
    }

    /**
     * 抛出异常
     */
    private void checkException() {
        if (null != e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }

    /**
     * 填充完成之后，唤醒一个线程继续执行
     *
     * @param ex
     */
    public void complete(Exception ex) {
        this.e = ex;
        this.currentState.getAndSet(FILLED_NEXT_STATE);
        synchronized (this) {
            this.notify();
        }
    }


    private void init() {
        currentSegment = new Segment();
        fillingUpdater = FillingSegmentUpdater.getInstance();
    }

    private boolean compareAndSwapCurrentState(int currentState, int nextState) {
        return this.currentState.compareAndSet(currentState, nextState);
    }

    public Segment getNextSegment() {
        return nextSegment;
    }

    public void setNextSegment(Segment nextSegment) {
        this.nextSegment = nextSegment;
    }

}
