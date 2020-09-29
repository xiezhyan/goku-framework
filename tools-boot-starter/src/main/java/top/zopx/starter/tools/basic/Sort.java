package top.zopx.starter.tools.basic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zopx.starter.tools.constants.Sorted;

/**
 * top.zopx.starter.tools.basic.Sort
 *
 * @author sanq.Yan
 * @date 2020/5/12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sort {

    private String field;

    private Sorted sorted = Sorted.ASC;
}
