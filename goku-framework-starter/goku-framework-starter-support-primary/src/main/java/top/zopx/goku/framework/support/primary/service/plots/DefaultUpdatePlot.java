package top.zopx.goku.framework.support.primary.service.plots;

import top.zopx.goku.framework.support.primary.core.service.IUpdatePlot;

import java.math.BigDecimal;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/1/27
 */
public class DefaultUpdatePlot implements IUpdatePlot {

    @Override
    public BigDecimal percent(String tag) {
        return BigDecimal.valueOf(0.8);
    }

    @Override
    public int segmentPullSize(String tag) {
        return 1000;
    }
}
