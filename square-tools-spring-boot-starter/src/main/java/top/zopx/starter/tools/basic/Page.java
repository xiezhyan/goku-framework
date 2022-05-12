package top.zopx.starter.tools.basic;

import java.io.Serializable;
import java.util.List;

/**
 * 返回分页查询后的数据
 *
 * @author 俗世游子
 * @date 2020/1/26
 */
public class Page<T> implements Serializable {

    // 分页参数
    private Pagination pagination;
    //结果集
    private List<T> datas;

    public Page() {
    }

    public Page(Pagination pagination, List<T> datas) {
        this.pagination = pagination;
        this.datas = datas;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
