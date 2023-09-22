package top.zopx.goku.framework.support.primary.core.service;

import java.math.BigDecimal;

/**
 *
 * @author Mr.Xie
 * @date 2022/1/17
 * @email xiezhyan@126.com
 */
public interface IUpdatePlot {

    /**
     * 百分比
     * @param tag 绑定key
     * @return BigDecimal
     */
    BigDecimal percent(String tag);

    /**
     * segment一次拉取的数量
     * @param tag 绑定key
     * @return int
     */
    int segmentPullSize(String tag);
}
