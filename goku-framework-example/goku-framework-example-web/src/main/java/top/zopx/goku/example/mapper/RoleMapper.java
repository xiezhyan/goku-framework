package top.zopx.goku.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zopx.goku.example.entity.Role;
import top.zopx.goku.example.entity.dto.RoleDTO;
import top.zopx.goku.framework.mybatis.base.IBaseMapper;

import java.util.List;

/**
 * 角色(Role)表数据库访问层
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-07-23 12:33:44
 */
@Mapper
public interface RoleMapper extends IBaseMapper<Role, RoleDTO> {
}

