package top.zopx.goku.framework.support.primary.core.buffer;

import top.zopx.goku.framework.support.primary.core.entity.IDGetter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 号段 [minId ~ maxId)
 *
 * @author Mr.Xie
 * @date 2022/1/17
 * @email xiezhyan@126.com
 */
@SuppressWarnings("all")
public class Segment {

    private long minID;
    private long maxID;

    /**
     * 记录当前ID
     */
    private final AtomicLong currentID = new AtomicLong();

    /**
     * 获取号段
     *
     * @param num 号段大小
     * @return IDWrapper
     */
    public IDGetter next(int num) {
        IDGetter idGetter = null;
        for (; ; ) {
            long currentID = this.currentID.get();
            long nextID = currentID + num;
            // 已经达到最大限度
            if (currentID == maxID) {
                break;
            }

            // 如果有号, 将currentID设置为nextID
            if (maxID >= nextID && compareAndSwapCurrentID(currentID, nextID)) {
                idGetter = new IDGetter();
                idGetter.setCurrentID(currentID);
                idGetter.setMinID(currentID);
                idGetter.setMaxID(nextID);
                break;
            }

            // 如果号段有，只是不够num个
            final long diff = maxID - currentID;
            if (nextID > maxID && diff > 0 && compareAndSwapCurrentID(currentID, maxID)) {
                idGetter = new IDGetter();
                idGetter.setCurrentID(currentID);
                idGetter.setMinID(currentID);
                idGetter.setMaxID(maxID);
                break;
            }
        }
        return idGetter;
    }

    /**
     * 求 号段消耗的百分比
     *
     * @return BigDecimal
     */
    public BigDecimal percent() {
        BigDecimal bigDecimal;
        //初始状态
        if (maxID == 0) {
            bigDecimal = new BigDecimal("1.00");
        } else {
            bigDecimal = new BigDecimal(currentID.get() - minID).divide(new BigDecimal(maxID - minID), 3, RoundingMode.UP);
        }
        return bigDecimal;
    }

    /**
     * 基于CAS的方式更新currentID的值
     *
     * @param currentID 当前值
     * @param x         期望值
     * @return 是否修改成功
     */
    private boolean compareAndSwapCurrentID(long currentID, long x) {
        return this.currentID.compareAndSet(currentID, x);
    }

    public long getMinID() {
        return minID;
    }

    public void setMinID(long minID) {
        this.minID = minID;
    }

    public long getMaxID() {
        return maxID;
    }

    public void setMaxID(long maxID) {
        this.maxID = maxID;
    }

    public long getCurrentID() {
        return this.currentID.get();
    }

    public void setCurrentID(long currentID) {
        this.currentID.getAndSet(currentID);
    }
}
