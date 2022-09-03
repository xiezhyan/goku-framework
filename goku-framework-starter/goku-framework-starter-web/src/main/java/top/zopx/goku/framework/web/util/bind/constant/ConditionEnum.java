package top.zopx.goku.framework.web.util.bind.constant;

import top.zopx.goku.framework.tools.constant.IEnum;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:43
 */
public enum ConditionEnum implements IEnum<String> {

    /**
     * 列表
     */
    GETLIST,
    /**
     * 查看
     */
    GETBYPRIKEY,
    ;

    ConditionEnum() {
        this.init(name(), name());
    }
}
