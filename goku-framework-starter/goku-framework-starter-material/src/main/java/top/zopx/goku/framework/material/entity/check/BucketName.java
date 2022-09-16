package top.zopx.goku.framework.material.entity.check;

import org.apache.commons.lang3.StringUtils;
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
        if (StringUtils.isBlank(name)) {
            throw new BusException("Bucket名称不能为空");
        }

        if (!Pattern.matches("^[a-z0-9-a-z0-9]+$", name)) {
            throw new BusException("Bucket的名称必须符合规范：只能包含小写字符和数字");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
