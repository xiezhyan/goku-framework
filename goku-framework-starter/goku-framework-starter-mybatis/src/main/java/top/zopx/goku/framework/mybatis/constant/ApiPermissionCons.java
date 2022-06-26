package top.zopx.goku.framework.mybatis.constant;

import top.zopx.goku.framework.tools.constant.IEnum;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/06/26 18:50
 */
public enum ApiPermissionCons implements IEnum<String> {

    /**
     * 列表
     */
    PAGE("PAGE", "列表", "/page"),
    /**
     * 详情
     */
    DETAIL("DETAIL", "详情", "/detail"),
    /**
     * 创建
     */
    SAVE("SAVE", "创建", "/save"),
    /**
     * 修改
     */
    UPDATE("UPDATE", "修改", "/update"),
    /**
     * 删除
     */
    DELETE("DELETE", "删除", "/delete"),
    /**
     * 导入
     */
    IMPORT("IMPORT", "导入", "/import"),
    /**
     * 导出
     */
    EXPORT("EXPORT", "导出", "/export"),
    ;

    private final String path;

    ApiPermissionCons(String key, String msg, String path) {
        init(key, msg);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
