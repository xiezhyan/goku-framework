package top.zopx.goku.framework.tools.constant.defaults;

import top.zopx.goku.framework.tools.constant.IEnum;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:06
 */
public enum SexEnum implements IEnum<String> {

    /**
     * 男
     */
    MAN("男", "男"),
    /**
     * 女
     */
    WOMAN("女", "女"),
    ;

    SexEnum(String code, String msg) {
        init(code, msg);
    }
}
