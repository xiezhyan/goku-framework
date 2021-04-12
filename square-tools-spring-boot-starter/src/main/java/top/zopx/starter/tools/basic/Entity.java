package top.zopx.starter.tools.basic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础实体类
 *  - 典型案例：数据权限参数配置
 *
 * @author sanq.Yan
 * @date 2020/11/24
 */
public class Entity implements Serializable {

    /**
     * 自定义参数
     */
    private Map<String, Object> map = new HashMap<>();

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
