package top.zopx.starter.tools.basic;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 *
 * @author 俗世游子
 * @date 2020/1/26
 */
public class Pagination implements Serializable {

    private static final long serialVersionUID = 1L;

    // 当前显示的条数,默认显示100条
    private int pageSize = 10;

    // 当前显示的页数
    private int currentIndex = 1;

    // 查询总数
    private long totalCount;

    // 排序方式
    private List<Sorted> sorteds;

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

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Sorted> getSorteds() {
        return sorteds;
    }

    public void setSorteds(List<Sorted> sorteds) {
        this.sorteds = sorteds;
    }
}
