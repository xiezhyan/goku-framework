package top.zopx.goku.framework.material.factory;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.material.service.IMaterialService;
import top.zopx.goku.framework.tools.exceptions.BusException;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/30
 */
@Component
public class ServiceFactory {

    @Resource
    private List<IMaterialService> materialServicesList;

    public IMaterialService get() {
        if (CollectionUtils.isEmpty(materialServicesList)) {
            throw new BusException("尚未完成初始化");
        }

        if (materialServicesList.size() == 1) {
            return materialServicesList.get(0);
        }

        return materialServicesList.get(
                RandomUtils.nextInt(0, materialServicesList.size())
        );
    }
}
