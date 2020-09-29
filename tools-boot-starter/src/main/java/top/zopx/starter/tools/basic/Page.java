package top.zopx.starter.tools.basic;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Page<T> implements Serializable {

    private Pagination pagination;

    //结果集
    private List<T> datas;
}
