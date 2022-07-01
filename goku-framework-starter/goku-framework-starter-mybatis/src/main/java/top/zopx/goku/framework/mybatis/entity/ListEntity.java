package top.zopx.goku.framework.mybatis.entity;

import top.zopx.goku.framework.web.util.validate.constant.ValidGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 集合接收对象
 *
 * @author 俗世游子
 * @date 2020/11/24
 */
public class ListEntity<T> implements Serializable {

    @NotNull(message = "集合对象不能为空", groups = ValidGroup.Delete.class)
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
