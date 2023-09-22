package top.zopx.goku.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zopx.goku.example.entity.Role;
import top.zopx.goku.example.entity.dto.RoleDTO;

import java.util.*;


/**
 * (Role)表数据库访问层
 *
 * @author Mr.Xie
 * @date 2023-05-17 15:02:52
 */
@Mapper
public interface RoleMapper {

    /**
     * 保存
     *
     * @param body 参数信息
     * @return 是否保存成功
     */
    int save(Role body);

    /**
     * 批量保存
     *
     * @param data 参数信息
     * @return 是否保存成功
     */
    int saveBatch(Collection<Role> data);

    /**
     * 修改信息
     *
     * @param body 参数信息
     * @param id   主键
     * @return 是否修改成功
     */
    int update(@Param("body") Role body, @Param("id") Long id);

    /**
     * 删除信息
     *
     * @param id 主键
     * @return 是否删除成功
     */
    int updateIsDeleteToYes(Long id, @Param("deleter") Long deleter);

    /**
     * 删除信息
     *
     * @param data 批量删除
     * @return 是否删除成功
     */
    int updateIsDeleteToYesBatch(@Param("data") Collection<Long> data, @Param("deleter") Long deleter);

    /**
     * 查看详情
     *
     * @param id 主键
     * @return 详细信息
     */
    Role getById(Long id);

    /**
     * 查看全部
     *
     * @param body     查询参数
     * @param startRow 开始范围
     * @param endRow   结束范围
     * @return 列表
     */
    List<Role> getList(@Param("body") RoleDTO body, @Param("startRow") Long startRow, @Param("endRow") Long endRow);
}


