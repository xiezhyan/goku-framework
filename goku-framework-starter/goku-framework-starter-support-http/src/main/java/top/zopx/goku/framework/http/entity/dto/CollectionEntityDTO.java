package top.zopx.goku.framework.http.entity.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Collection;

/**
 * 集合接收对象
 *
 * @author Mr.Xie
 * @date 2020/11/24
 */
public class CollectionEntityDTO<T extends Serializable> implements Serializable {

    @NotNull(message = "collection.data.isnull")
    @Valid
    private Collection<T> data;

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }
}
