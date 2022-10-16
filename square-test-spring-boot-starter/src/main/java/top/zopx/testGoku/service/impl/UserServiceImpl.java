package top.zopx.testGoku.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.zopx.goku.framework.mybatis.base.BaseServiceImpl;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.testGoku.entity.User;
import top.zopx.testGoku.entity.dto.UserDTO;
import top.zopx.testGoku.entity.mapstruct.UserMapstruct;
import top.zopx.testGoku.entity.vo.UserVO;
import top.zopx.testGoku.mapper.UserMapper;
import top.zopx.testGoku.service.UserService;

import javax.annotation.Resource;

/**
 * 用户表(User)表服务实现类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-08-12 16:56:52
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<UserVO, UserDTO, User, UserMapper> implements UserService {

    @Resource
    private UserMapstruct userMapstruct;

    @Override
    public void doSearchBefore(UserDTO query) {
        if (StringUtils.isBlank(query.getUserType())) {
            throw new BusException("请指定需要查询的用户类型");
        }
    }

    @Override
    public User doDeleteBefore(Long id) {
        return getById(id);
    }

    @Override
    protected UserVO copyToVO(User data) {
        return userMapstruct.copyDataToVO(data);
    }

    @Override
    protected User copyToEntity(UserDTO body) {
        return userMapstruct.copyDtoToData(body);
    }
}