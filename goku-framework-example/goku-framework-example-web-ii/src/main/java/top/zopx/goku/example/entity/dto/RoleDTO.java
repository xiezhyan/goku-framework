package top.zopx.goku.example.entity.dto;

import java.time.LocalDateTime;
// 父类继承
import top.zopx.goku.framework.http.entity.dto.EntityDTO;

/**
 * (Role)表实体类
 *
 * @author Mr.Xie
 * @date 2023-05-17 15:02:53
 */
public class RoleDTO extends EntityDTO {
    private String key;
    private String roleName;
    private Integer status;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

