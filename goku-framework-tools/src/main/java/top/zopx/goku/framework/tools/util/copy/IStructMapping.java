package top.zopx.goku.framework.tools.util.copy;

import java.util.List;

/**
 * 基础转换
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/3/9
 */
@SuppressWarnings("all")
public interface IStructMapping<EntityDTO, EntityVO, Entity> extends IStruct<EntityDTO, Entity> {

    /**
     * entity -> entityVO
     *
     * @param entity entity
     * @return entityVO
     */
    EntityVO copyToV(Entity entity);

    /**
     * entity -> entityVO
     *
     * @param entityList entity
     * @return entityVO
     */
    List<EntityVO> copyToV(List<Entity> entityList);
}
