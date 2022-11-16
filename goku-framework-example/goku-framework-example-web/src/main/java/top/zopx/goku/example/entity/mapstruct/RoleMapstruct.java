package top.zopx.goku.example.entity.mapstruct;

import org.mapstruct.Mapper;
import top.zopx.goku.example.entity.Role;
import top.zopx.goku.example.entity.dto.RoleDTO;
import top.zopx.goku.example.entity.vo.RoleVO;
import top.zopx.goku.framework.tools.util.copy.IStructPojo;

/**
 * 角色(Role)表实体类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-07-23 12:33:44
 */
@Mapper(componentModel = "spring")
public interface RoleMapstruct extends IStructPojo<Role, RoleVO, RoleDTO> {
}

