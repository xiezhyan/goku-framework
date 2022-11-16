package top.zopx.goku.example.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zopx.goku.example.entity.Role;
import top.zopx.goku.example.entity.dto.RoleDTO;
import top.zopx.goku.example.entity.mapstruct.RoleMapstruct;
import top.zopx.goku.example.entity.vo.RoleVO;
import top.zopx.goku.example.mapper.RoleMapper;
import top.zopx.goku.example.service.RoleService;
import top.zopx.goku.framework.mybatis.base.BaseServiceImpl;
import top.zopx.goku.framework.mybatis.util.UserLoginHelper;
import top.zopx.goku.framework.tools.constant.defaults.YesOrNoEnum;
import top.zopx.goku.framework.tools.exceptions.BusException;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 角色(Role)表服务实现类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-08-12 16:56:52
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<RoleVO, RoleDTO, Role, RoleMapper> implements RoleService {

    @Resource
    private RoleMapstruct roleMapstruct;

    @Override
    public Role doDeleteBefore(Long id) {
        return getById(id);
    }

    @Override
    protected RoleVO copyToVO(Role data) {
        return roleMapstruct.copyDataToVO(data);
    }

    @Override
    protected Role copyToEntity(RoleDTO body) {
        return roleMapstruct.copyDtoToData(body);
    }
}

