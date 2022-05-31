package top.zopx.goku.framework.material.util;

import top.zopx.goku.framework.tools.util.string.StringUtil;

import java.text.MessageFormat;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/31
 */
public final class ObjectNameUtil {

    /**
     * 基于UUID生成新的文件名称
     *
     * @param originFileName 原始文件名称
     * @return 新文件名称
     */
    public static String getNewFileName(String originFileName) {
        return MessageFormat.format(
                "{0}{1}",
                StringUtil.uuid(),
                originFileName.substring(originFileName.lastIndexOf(".")));
    }

}
