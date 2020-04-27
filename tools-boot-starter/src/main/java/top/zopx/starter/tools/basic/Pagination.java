package top.zopx.starter.tools.basic;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * version: 分页
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
@Data
@NoArgsConstructor
public class Pagination implements Serializable {

    private static final long serialVersionUID = 1L;

    //当前显示的条数,默认显示20条
    private long pageSize = 20L;

    //当前显示的页数
    private long currentIndex;

    //查询结果总条数
    private long totalCount;

    public Pagination(int pageSize, int currentIndex) {
        this.pageSize = pageSize;
        this.currentIndex = currentIndex;
    }

    long getStartPage() {
        if (this.currentIndex <= 0)
            this.currentIndex = 1;
        if (this.currentIndex >= getTotalPage())
            this.currentIndex = getTotalPage();
        long startPage = (this.currentIndex - 1) * this.pageSize;
        return startPage < 0 ? 0 : startPage;
    }

    long getTotalPage() {
        return this.totalCount % this.pageSize != 0 ?    //
                this.totalCount / this.pageSize + 1 :            //
                this.totalCount / this.pageSize;
    }

    public long getPageSize() {
        if (pageSize > 200L)
            return 200L;
        return pageSize;
    }
}
