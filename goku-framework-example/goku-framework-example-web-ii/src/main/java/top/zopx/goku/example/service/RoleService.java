package top.zopx.goku.example.service;

// 继承父类

import top.zopx.goku.example.entity.vo.RoleVO;
import top.zopx.goku.example.entity.dto.RoleDTO;
import top.zopx.goku.framework.tools.entity.vo.Pagination;

import java.util.*;

/**
 * (Role)表服务接口
 *
 * @author Mr.Xie
 * @date 2023-05-17 15:02:52
 */
public interface RoleService {


    /**
     * 保存
     *
     * @param body 参数信息
     * @return 是否保存成功
     */
    Boolean save(RoleDTO body);

    /**
     * 批量保存
     *
     * @param data 参数信息
     * @return 是否保存成功
     */
    Boolean save(Collection<RoleDTO> data);

    /**
     * 修改信息
     *
     * @param body 参数信息
     * @param id   主键
     * @return 是否修改成功
     */
    Boolean update(RoleDTO body, Long id);

    /**
     * 删除信息
     *
     * @param id 主键
     * @return 是否删除成功
     */
    Boolean delete(Long id);

    /**
     * 删除信息
     *
     * @param data 批量删除
     * @return 是否删除成功
     */
    Boolean delete(Collection<Long> data);

    /**
     * 查看详情
     *
     * @param id 主键
     * @return 详细信息
     */
    RoleVO get(Long id);

    /**
     * 查看全部
     *
     * @param body 查询参数
     * @return 列表
     */
    List<RoleVO> list(RoleDTO body);

    /**
     * 分页
     *
     * @param body       查询参数
     * @param pagination 分页信息
     * @return 列表
     */
    List<RoleVO> list(RoleDTO body, Pagination pagination);

}

