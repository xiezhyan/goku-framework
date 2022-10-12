package top.zopx.testGoku.entity.mapstruct;

import org.mapstruct.Mapper;
import top.zopx.goku.framework.tools.util.copy.IStructPojo;
import top.zopx.testGoku.entity.User;
import top.zopx.testGoku.entity.dto.UserDTO;
import top.zopx.testGoku.entity.vo.UserVO;

/**
 * 用户表(User)表实体类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-07-23 12:33:43
 */
@Mapper(componentModel = "spring")
public interface UserMapstruct extends IStructPojo<User, UserVO, UserDTO> {
}

