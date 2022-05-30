package top.zopx.goku.framework.material.entity.check;

import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.tools.util.string.StringUtil;

import java.util.regex.Pattern;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/30
 */
public class BucketName {

    private final String name;

    public BucketName(String name) {
        if (StringUtil.isEmpty(name)) {
            throw new BusException("名称不能为空");
        }

        if (!Pattern.matches("^[a-z0-9]+$", name)) {
            throw new BusException("bucket的名称必须符合规范：只能包含小写字符和数字");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
