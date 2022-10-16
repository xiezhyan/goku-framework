package top.zopx.testGoku.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.zopx.goku.framework.mybatis.base.IBaseMapper;
import top.zopx.testGoku.entity.User;
import top.zopx.testGoku.entity.dto.UserDTO;

/**
 * 用户表(User)表数据库访问层
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-07-23 12:33:42
 */
@Mapper
public interface UserMapper extends IBaseMapper<User, UserDTO> {
}

