package top.zopx.goku.framework.jpa.code.service;

import top.zopx.goku.framework.http.entity.dto.EntityDTO;
import top.zopx.goku.framework.http.entity.vo.EntityVO;
import top.zopx.goku.framework.tools.entity.vo.Pagination;

import java.util.Collection;
import java.util.List;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/4/2 11:13
 */
public interface IService
        <E extends EntityDTO, V extends EntityVO> {

    /**
     * 保存
     *
     * @param body 参数信息
     * @return 是否保存成功
     */
    Boolean save(E body);

    /**
     * 批量保存
     *
     * @param data 参数信息
     * @return 是否保存成功
     */
    Boolean save(Collection<E> data);

    /**
     * 修改信息
     *
     * @param body 参数信息
     * @param id   主键
     * @return 是否修改成功
     */
    Boolean update(E body, Long id);

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
    V get(Long id);

    /**
     * 查看全部
     *
     * @param body 查询参数
     * @return 列表
     */
    List<V> list(E body);

    /**
     * 分页
     *
     * @param body       查询参数
     * @param pagination 分页信息
     * @return 列表
     */
    List<V> list(E body, Pagination pagination);
}
