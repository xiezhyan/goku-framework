package top.zopx.starter.tools.basic;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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

    // 排序
    private List<Sort> sorts;
}
