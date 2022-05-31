package top.zopx.goku.framework.material.entity.check;

import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.tools.util.string.StringUtil;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/30
 */
public class ObjectName {

    private final String name;

    public ObjectName(String name) {
        if (StringUtil.isEmpty(name)) {
            throw new BusException("对象名称不能为空");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }
}
