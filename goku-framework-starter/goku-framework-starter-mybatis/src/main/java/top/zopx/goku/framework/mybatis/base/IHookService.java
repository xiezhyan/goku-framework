package top.zopx.goku.framework.mybatis.base;


import top.zopx.goku.framework.mybatis.entity.DataEntity;

/**
 * 钩子函数
 *
 * @author 俗世游子
 * @date 2022/05/12
 */
public interface IHookService<DTO, Entity extends DataEntity> {

    /**
     * 查询钩子函数
     *
     * @param dto 入参
     */
    default void doSearchBefore(DTO dto) {
    }

    /**
     * 修改钩子函数
     *
     * @param data 入参
     * @param dto  入参
     */
    default void doUpdateBefore(Entity data, DTO dto) {
    }

    /**
     * 修改钩子函数
     *
     * @param data 入参
     * @return 是否处理成功
     */
    default Boolean doUpdateAfter(Entity data) {
        return true;
    }

    /**
     * 创建钩子函数
     *
     * @param dto    入参
     * @param entity 入参
     */
    default void doCreateBefore(Entity entity, DTO dto) {
    }

    /**
     * 创建成功之后的
     *
     * @param entity 参数
     * @return 是否处理成功
     */
    default Boolean doCreateAfter(Entity entity, DTO dto) {
        return true;
    }

    /**
     * 删除钩子函数
     *
     * @param id 主键
     * @return Entity
     */
    default Entity doDeleteBefore(Long id) {
        return null;
    }

}
