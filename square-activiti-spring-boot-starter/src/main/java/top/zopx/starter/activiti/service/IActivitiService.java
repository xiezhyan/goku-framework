package top.zopx.starter.activiti.service;

import top.zopx.starter.activiti.entity.request.ModelRequest;
import top.zopx.starter.activiti.entity.response.ModelResponse;
import top.zopx.starter.tools.basic.Pagination;

import java.util.List;

/**
 * @author 俗世游子
 * @date 2021/6/26
 * @email xiezhyan@126.com
 */
public interface IActivitiService {

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
     * 获取流程列表
     *
     * @param request    搜索对象
     * @param pagination 分页对象
     * @return List<ModelResponse>
     */
    List<ModelResponse> getList(ModelRequest request, Pagination pagination);

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

    /**
     * 通过流程实例ID获取模式
     *
     * @param processDefinitionId 流程实例ID
     * @return ModelResponse
     */
    ModelResponse getById(String processDefinitionId);
}
