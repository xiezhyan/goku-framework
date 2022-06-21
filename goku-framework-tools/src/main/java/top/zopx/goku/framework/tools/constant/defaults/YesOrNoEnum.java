package top.zopx.goku.framework.tools.constant.defaults;

import top.zopx.goku.framework.tools.constant.IEnum;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:06
 */
public enum YesOrNoEnum implements IEnum<Integer> {

    /**
     * 是
     */
    YES(1, "是"),
    /**
     * 否
     */
    NO(0, "否"),
    ;

    YesOrNoEnum(Integer code, String msg) {
        init(code, msg);
    }
}
