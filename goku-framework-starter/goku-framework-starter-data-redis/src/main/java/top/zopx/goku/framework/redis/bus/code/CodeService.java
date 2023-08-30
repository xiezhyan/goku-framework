package top.zopx.goku.framework.redis.bus.code;

import top.zopx.goku.framework.redis.bus.constant.CodeTypeEnum;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/6/7 21:00
 */
public interface CodeService {

    /**
     * 生成code
     * @param type 类型
     * @param key 标签
     * @return CodeVO
     */
    CodeVO genericCode(CodeTypeEnum type, String key);

    /**
     * 校验
     * @param key 标签
     * @param code code
     * @return
     */
    void check(String key, String code);
}
