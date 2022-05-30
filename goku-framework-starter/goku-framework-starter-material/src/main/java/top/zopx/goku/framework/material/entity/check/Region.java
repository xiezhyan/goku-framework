package top.zopx.goku.framework.material.entity.check;

import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.tools.util.string.StringUtil;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/30
 */
public class Region {

    private final String region;

    public Region(String region) {
        if (StringUtil.isEmpty(region)) {
            throw new BusException("地区不能为空");
        }
        this.region = region;
    }

    public String getRegion() {
        return region;
    }
}
