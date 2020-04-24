package top.zopx.starter.tools.basic;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * version: 返回分页查询后的数据
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
@Data
@NoArgsConstructor
public class Page<T> implements Serializable {

    private Pagination pagination;

    //结果集
    private List<T> datas;

    //页码列表的开始索引
    private int beginPageIndex;

    //页码列表的结束索引
    private int endPageIndex;

    public Page(Pagination pagination, List<T> datas) {
        this.datas = datas;
        this.pagination = pagination;

        if (pagination.getTotalPage() < 10) {
            beginPageIndex = 1;
            endPageIndex = pagination.getTotalPage();
        } else {
            beginPageIndex = pagination.getCurrentIndex() - 4;
            endPageIndex = pagination.getCurrentIndex() + 5;

            if (beginPageIndex < 1) {
                beginPageIndex = 1;
                endPageIndex = 10;
            } else if (endPageIndex > pagination.getTotalPage()) {
                endPageIndex = pagination.getTotalPage();
                beginPageIndex = pagination.getTotalPage() - 9;
            }
        }
    }
}
