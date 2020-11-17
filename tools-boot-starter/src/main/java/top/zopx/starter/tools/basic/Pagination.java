package top.zopx.starter.tools.basic;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public class Pagination implements Serializable {

    private static final long serialVersionUID = 1L;

    // 当前显示的条数,默认显示100条
    private int pageSize = 10;

    // 当前显示的页数
    private int currentIndex = 1;

    // 排序
    private List<Sort> sorts;

    // 查询总数
    private int totalCount;

    public Pagination() {
    }

    public Pagination(int totalCount) {
        this.totalCount = totalCount;
    }

    public Pagination(int pageSize, int currentIndex, int totalCount) {
        this.pageSize = pageSize;
        this.currentIndex = currentIndex;
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
