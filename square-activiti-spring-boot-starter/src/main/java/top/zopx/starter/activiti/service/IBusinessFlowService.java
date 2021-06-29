package top.zopx.starter.activiti.service;

import top.zopx.starter.activiti.entity.response.TaskResponse;
import top.zopx.starter.activiti.entity.response.CompleteResponse;
import top.zopx.starter.activiti.entity.response.HistoryResponse;
import top.zopx.starter.tools.basic.Pagination;

import java.util.List;
import java.util.Map;

/**
 * @author 俗世游子
 * @date 2021/6/26
 * @email xiezhyan@126.com
 */
public interface IBusinessFlowService {

    /**
     * 启动任务
     *
     * @param variables            判断参数
     * @param businessKey          业务关联键
     * @param processDefinitionKey 流程图的流程名称：唯一key
     */
    boolean startProcessByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables);

    /**
     * 任务提交，进入下一步
     *
     * @param businessKey 业务关联键
     * @param map         参数
     * @return CompleteResponse
     */
    CompleteResponse completeByTaskId(String businessKey, Map<String, Object> map);

    /**
     * 待办任务
     *
     * @param businessKey 业务关联键
     * @param userId      当前用户
     * @param isActive    是否是活动状态
     */
    List<TaskResponse> getAgentTaskListByAssignee(String businessKey, String userId, boolean isActive);


    /**
     * 撤销流程
     *
     * @param businessKey          业务关联键
     * @param processDefinitionKey 流程唯一标识
     * @param userId               当前用户
     * @param reason               撤销原因
     */
    boolean revokeFlow(String processDefinitionKey, String businessKey, String userId, String reason);

    /**
     * 历史任务
     *
     * @param processDefinitionKey 流程唯一标识
     * @param businessKey          业务关联键
     * @param userId               当前用户
     * @param pagination           分页对象
     * @return List<HistoryFlowResponse>
     */
    List<HistoryResponse> getHistoryTaskList(String processDefinitionKey, String businessKey, String userId, Pagination pagination);

    /**
     * 历史流程
     *
     * @param processDefinitionKey 流程唯一标识
     * @param businessKey          业务关联键
     * @param userId               当前用户
     * @return List<HistoryFlowResponse>
     */
    List<HistoryResponse> getHistoryTaskInstanceById(String processDefinitionKey, String businessKey, String userId);

}
