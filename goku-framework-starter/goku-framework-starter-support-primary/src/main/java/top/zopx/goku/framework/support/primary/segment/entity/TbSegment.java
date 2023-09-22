package top.zopx.goku.framework.support.primary.segment.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/1/26
 */
public class TbSegment implements Serializable {

    private Long id;

    private String busKey;

    private Long maxID;

    private Integer step;

    private Date updateTime;

    public TbSegment() {
    }

    public TbSegment(Long id, String busKey, Long maxID, Integer step, Date updateTime) {
        this.id = id;
        this.busKey = busKey;
        this.maxID = maxID;
        this.step = step;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusKey() {
        return busKey;
    }

    public void setBusKey(String busKey) {
        this.busKey = busKey;
    }

    public Long getMaxID() {
        return maxID;
    }

    public void setMaxID(Long maxID) {
        this.maxID = maxID;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
