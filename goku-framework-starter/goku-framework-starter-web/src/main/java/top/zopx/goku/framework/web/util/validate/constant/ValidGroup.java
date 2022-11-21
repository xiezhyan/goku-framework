package top.zopx.goku.framework.web.util.validate.constant;

import javax.validation.groups.Default;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/10/15
 */
public class ValidGroup {
    public interface Create extends Default {}

    public interface Update extends Default {}

    public interface Delete extends Default {}
}
