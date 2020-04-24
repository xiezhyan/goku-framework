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
    private int pageSize = 20;

    //当前显示的页数
    private int currentIndex;

    //查询结果总条数
    private int totalCount;

    public Pagination(int pageSize, int currentIndex) {
        this.pageSize = pageSize;
        this.currentIndex = currentIndex;
    }

    int getStartPage() {
        if (this.currentIndex <= 0)
            this.currentIndex = 1;
        if (this.currentIndex >= getTotalPage())
            this.currentIndex = getTotalPage();
        int startPage = (this.currentIndex - 1) * this.pageSize;
        return startPage < 0 ? 0 : startPage;
    }

    int getTotalPage() {
        return this.totalCount % this.pageSize != 0 ?    //
                this.totalCount / this.pageSize + 1 :            //
                this.totalCount / this.pageSize;
    }

    public int getPageSize() {
        if (pageSize > 200)
            return 200;
        return pageSize;
    }
}
