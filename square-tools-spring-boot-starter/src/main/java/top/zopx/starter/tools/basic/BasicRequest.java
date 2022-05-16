package top.zopx.starter.tools.basic;

import top.zopx.starter.tools.tools.strings.StringUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 基础实体类
 * - 典型案例：数据权限参数配置
 *
 * @author 俗世游子
 * @date 2020/11/24
 */
public class BasicRequest implements Serializable {

    /**
     * 自定义参数
     */
    private Map<String, Object> map = new HashMap<>(8);

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public BasicRequest put(String key, Object value) {
        if (StringUtil.isBlank(key) || Objects.isNull(value)) {
            return this;
        }
        this.map.put(key, value);
        return this;
    }

    /**
     * 获取指定key的内容
     *
     * @param key key
     * @return 字符串
     */
    public String getValueToString(String key) {
        return map.getOrDefault(key, "").toString();
    }

    /**
     * 获取指定key的内容
     *
     * @param key key
     * @return Long
     */
    public Long getValueToLong(String key) {
        return StringUtil.toLong(map.getOrDefault(key, "-1"));
    }

    /**
     * 获取指定key的内容
     *
     * @param key key
     * @return Integer
     */
    public Integer getValueToInteger(String key) {
        return StringUtil.toInteger(map.getOrDefault(key, "-1"));
    }


}
