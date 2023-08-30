package top.zopx.goku.framework.http.entity.dto;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import top.zopx.goku.framework.http.util.validate.constant.ValidGroup;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 基础实体类
 *
 * @author Mr.Xie
 * @date 2020/11/24
 */
public class EntityDTO implements Serializable {

    @NotNull(message = "主键不能为空", groups = {ValidGroup.Update.class, ValidGroup.Delete.class})
    private Long id;
    /**
     * 自定义参数
     */
    private transient Map<String, Object> map = new HashMap<>(8);

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntityDTO put(String key, Object value) {
        if (StringUtils.isBlank(key) || Objects.isNull(value)) {
            return this;
        }
        this.map.put(key, value);
        return this;
    }

    public Object getMapByKey(String key) {
        return map.get(key);
    }
}
