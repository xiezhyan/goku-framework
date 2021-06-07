package top.zopx.starter.tools.tools.json;

import top.zopx.starter.tools.constants.JsonType;
import top.zopx.starter.tools.tools.json.impl.FJsonUtil;
import top.zopx.starter.tools.tools.json.impl.GJsonUtil;

/**
 * 总入口
 *
 * @author xiezhongyan
 * @date 2021/6/7
 * @email xiezhyan@126.com
 */
public enum JsonTool {

    /**
     * 单例
     */
    INSTANCE,
    ;

    public IJson get(JsonType jsonType) {
        switch (jsonType) {
            case GSON:
                return GJsonUtil.INSTANCE;
            case FAST_JSON:
            default:
                return FJsonUtil.INSTANCE;
        }
    }

}
