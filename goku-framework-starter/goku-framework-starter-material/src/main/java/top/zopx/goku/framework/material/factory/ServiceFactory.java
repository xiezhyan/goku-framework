package top.zopx.goku.framework.material.factory;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.material.service.IMaterialService;
import top.zopx.goku.framework.tools.exceptions.BusException;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/30
 */
@Component
public class ServiceFactory {

    @Resource
    private List<IMaterialService> materialServicesList;
    @Resource
    private Map<String, IMaterialService> materialServiceMap;

    /**
     *  bean name
     *
     * @return minio
     *         oss
     */
    public Map<String, IMaterialService> getMaterialServiceMap() {
        if (MapUtils.isEmpty(materialServiceMap)) {
            throw new BusException("尚未完成初始化");
        }
        return materialServiceMap;
    }

    public List<IMaterialService> getServiceList() {
        if (CollectionUtils.isEmpty(materialServicesList)) {
            throw new BusException("尚未完成初始化");
        }
        return materialServicesList;
    }

    public IMaterialService get() {
        if (CollectionUtils.isEmpty(materialServicesList)) {
            throw new BusException("尚未完成初始化");
        }
        return materialServicesList.get(0);
    }
}
