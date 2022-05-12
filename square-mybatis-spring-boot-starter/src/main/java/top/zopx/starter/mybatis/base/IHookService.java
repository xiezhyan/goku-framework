package top.zopx.starter.mybatis.base;


/**
 * 钩子函数
 *
 * @author 俗世游子
 * @date 2022/05/12
 */
public interface IHookService<Request, Entity> {


    /**
     * 修改钩子函数
     *
     * @param data 入参
     */
    default void doUpdateBefore(Entity data) {
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
     * @param request 入参
     */
    default void doCreateBefore(Request request) {
    }

    /**
     * 创建成功之后的
     *
     * @param entity 参数
     * @return 是否处理成功
     */
    default Boolean doCreateAfter(Entity entity) {
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
