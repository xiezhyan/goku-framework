package top.zopx.starter.tools.basic;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@AllArgsConstructor
@Builder
public class Pagination implements Serializable {

    private static final long serialVersionUID = 1L;

    // 当前显示的条数,默认显示20条
    private long pageSize = 20L;

    // 当前显示的页数
    private long currentIndex;

    // 排序
    private List<Sort> sorts;

    // 查询总数
    private long totalCount;
}
