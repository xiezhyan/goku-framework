package top.zopx.testGoku.service;

// 继承父类

import top.zopx.goku.framework.mybatis.base.IBaseService;
import top.zopx.testGoku.entity.User;
import top.zopx.testGoku.entity.dto.UserDTO;
import top.zopx.testGoku.entity.vo.UserVO;

/**
 * 用户表(User)表服务接口
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-07-23 12:33:42
 */
public interface UserService extends IBaseService<UserVO, UserDTO, User> {
}

