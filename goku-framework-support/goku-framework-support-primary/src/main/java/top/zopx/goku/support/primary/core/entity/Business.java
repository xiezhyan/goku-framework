package top.zopx.goku.support.primary.core.entity;

/**
 * 业务表
 * @author 俗世游子
 * @date 2022/1/17
 * @email xiezhyan@126.com
 */
public class Business {

    /**
     * 业务唯一标识
     */
    private String tag;

    /**
     * 最小ID
     */
    private Long minID;

    /**
     * 当前ID
     */
    private Long currentID;

    /**
     * 最大ID
     */
    private Long maxID;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getMinID() {
        return minID;
    }

    public void setMinID(Long minID) {
        this.minID = minID;
    }

    public Long getCurrentID() {
        return currentID;
    }

    public void setCurrentID(Long currentID) {
        this.currentID = currentID;
    }

    public Long getMaxID() {
        return maxID;
    }

    public void setMaxID(Long maxID) {
        this.maxID = maxID;
    }
}
