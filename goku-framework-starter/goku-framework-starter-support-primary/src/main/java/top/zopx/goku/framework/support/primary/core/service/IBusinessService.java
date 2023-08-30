package top.zopx.goku.framework.support.primary.core.service;

import top.zopx.goku.framework.support.primary.core.entity.Business;

/**
 * @author Mr.Xie
 * @date 2022/1/17
 * @email xiezhyan@126.com
 */
public interface IBusinessService {

    /**
     * 通过业务标识获取
     *
     * @param key      业务标识
     * @param pullSize 一次获取的ID数
     * @return Business
     */
    Business getBusinessByKey(String key, int pullSize);

}
