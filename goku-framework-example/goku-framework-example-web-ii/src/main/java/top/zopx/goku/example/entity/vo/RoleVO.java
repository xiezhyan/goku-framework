package top.zopx.goku.example.entity.vo;

import java.time.LocalDateTime;
// 父类继承
import top.zopx.goku.framework.http.entity.vo.EntityVO;

/**
 * (Role)表实体类
 *
 * @author Mr.Xie
 * @date 2023-05-17 15:02:53
 */
public class RoleVO extends EntityVO {
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

