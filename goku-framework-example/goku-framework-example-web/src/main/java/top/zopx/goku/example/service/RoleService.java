package top.zopx.goku.example.service;

// 继承父类

import top.zopx.goku.example.entity.Role;
import top.zopx.goku.example.entity.dto.RoleDTO;
import top.zopx.goku.example.entity.vo.RoleVO;
import top.zopx.goku.framework.mybatis.base.IBaseService;

import java.util.List;

/**
 * 角色(Role)表服务接口
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-07-23 12:33:44
 */
public interface RoleService extends IBaseService<RoleVO, RoleDTO, Role> {
}

