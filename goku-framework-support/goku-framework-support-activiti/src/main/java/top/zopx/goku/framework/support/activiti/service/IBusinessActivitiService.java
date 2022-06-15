package top.zopx.goku.framework.support.activiti.service;

/**
 * @author 俗世游子
 * @date 2021/6/26
 * @email xiezhyan@126.com
 */
public interface IBusinessActivitiService {

    /**
     * 跳转到编辑流程页面
     *
     * @param modelId  流程ID
     * @param tenantId 租户
     * @param category 分类
     * @return 返回流程链接
     */
    String saveOrUpdate(String modelId, String tenantId, String category);

    /**
     * 流程部署
     *
     * @param modelId 流程ID
     * @return true | false
     */
    boolean deploy(String modelId);

    /**
     * 删除流程
     *
     * @param modelId 流程ID
     * @return true | false
     */
    boolean deleteByModelId(String modelId);
}
