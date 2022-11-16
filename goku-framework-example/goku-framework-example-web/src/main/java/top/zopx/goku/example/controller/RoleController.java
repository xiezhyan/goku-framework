package top.zopx.goku.example.controller;

import org.springframework.web.bind.annotation.*;
import top.zopx.goku.example.entity.Role;
import top.zopx.goku.example.entity.dto.RoleDTO;
import top.zopx.goku.example.entity.vo.RoleVO;
import top.zopx.goku.example.service.RoleService;
import top.zopx.goku.framework.mybatis.base.BaseController;
import top.zopx.goku.framework.web.util.bind.annotation.Bind;

/**
 * 角色(Role)表控制层
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-07-23 12:33:44
 */
@Bind
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<RoleVO, RoleDTO, Role, RoleService> {

    // /role/page?sorteds[0].field=createTime
}

