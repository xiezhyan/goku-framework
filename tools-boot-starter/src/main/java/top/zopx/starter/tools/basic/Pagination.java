package top.zopx.starter.tools.basic;

import java.io.Serializable;
import java.util.List;

/**
 * version: 分页
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public class Pagination implements Serializable {

    private static final long serialVersionUID = 1L;

    // 当前显示的条数,默认显示20条
    private long pageSize = 20L;

    // 当前显示的页数
    private long currentIndex;

    // 排序
    private List<Sort> sorts;

    // 查询总数
    private long totalCount;

    public Pagination() {
    }

    public Pagination(long pageSize, long currentIndex, long totalCount) {
        this.pageSize = pageSize;
        this.currentIndex = currentIndex;
        this.totalCount = totalCount;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(long currentIndex) {
        this.currentIndex = currentIndex;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
