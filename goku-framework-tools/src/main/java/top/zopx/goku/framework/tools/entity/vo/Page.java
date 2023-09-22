package top.zopx.goku.framework.tools.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 返回分页查询后的数据
 *
 * @author Mr.Xie
 * @date 2020/1/26
 */
public class Page<T extends Serializable> implements Serializable {

    /**
     * 分页参数
     */
    private Pagination pagination;
    /**
     * 结果集
     */
    private List<T> data;

    public Page() {
    }

    public Page(Pagination pagination, List<T> data) {
        this.pagination = pagination;
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
