package top.zopx.goku.framework.http.entity.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * 集合接收对象
 *
 * @author Mr.Xie
 * @date 2020/11/24
 */
public class SingleEntityDTO<T extends Serializable> implements Serializable {

    @NotNull(message = "single.data.isnull")
    @Valid
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
