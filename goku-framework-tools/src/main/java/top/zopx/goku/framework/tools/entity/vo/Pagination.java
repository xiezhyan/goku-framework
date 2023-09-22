package top.zopx.goku.framework.tools.entity.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 *
 * @author Mr.Xie
 * @date 2020/1/26
 */
public class Pagination implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 当前显示的条数,默认显示10条
     */
    private Integer pageSize;

    /**
     * 当前显示的页数
     */
    private Integer currentIndex;

    /**
     * 开始行
     */
    private Long startRow;

    /**
     * 结束行
     */
    private Long endRow;

    /**
     * 查询总数
     */
    private Long totalCount;

    /**
     * 排序方式
     */
    private List<Sorted> sorted;

    public Pagination() {
    }

    public Pagination(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Pagination(Integer pageSize, Integer currentIndex, Long totalCount) {
        this.pageSize = pageSize;
        this.currentIndex = currentIndex;
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(Integer currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Long getStartRow() {
        return startRow;
    }

    public void setStartRow(Long startRow) {
        this.startRow = startRow;
    }

    public Long getEndRow() {
        return endRow;
    }

    public void setEndRow(Long endRow) {
        this.endRow = endRow;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Sorted> getSorted() {
        return sorted;
    }

    public void setSorted(List<Sorted> sorted) {
        this.sorted = sorted;
    }
}
