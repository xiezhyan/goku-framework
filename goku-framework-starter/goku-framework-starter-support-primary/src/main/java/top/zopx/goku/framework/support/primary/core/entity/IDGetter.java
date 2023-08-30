package top.zopx.goku.framework.support.primary.core.entity;

import java.io.Serializable;

/**
 * @author Mr.Xie
 * @date 2022/1/17
 * @email xiezhyan@126.com
 */
@SuppressWarnings("all")
public class IDGetter implements Serializable {

    private Long minID;

    private Long maxID;

    private Long currentID;

    public Long getMinID() {
        return minID;
    }

    public void setMinID(Long minID) {
        this.minID = minID;
    }

    public Long getMaxID() {
        return maxID;
    }

    public void setMaxID(Long maxID) {
        this.maxID = maxID;
    }

    public Long getCurrentID() {
        return currentID;
    }

    public void setCurrentID(Long currentID) {
        this.currentID = currentID;
    }

    @Override
    public String toString() {
        return "IDGetter{" +
                "minID=" + minID +
                ", maxID=" + maxID +
                ", currentID=" + currentID +
                '}';
    }
}
