package top.zopx.starter.activiti.service.impl;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;
import top.zopx.starter.activiti.constant.VariableConstant;
import top.zopx.starter.activiti.entity.response.*;
import top.zopx.starter.activiti.service.IBusinessFlowService;
import top.zopx.starter.tools.basic.Pagination;
import top.zopx.starter.tools.exceptions.BusException;
import top.zopx.starter.tools.tools.strings.StringUtil;
import top.zopx.starter.tools.tools.web.LogUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 俗世游子
 * @date 2021/6/29
 * @email xiezhyan@126.com
 */
public class BusinessFlowServiceImpl implements IBusinessFlowService {

    @Resource
    private TaskService taskService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private HistoryService historyService;

    @Override
    public boolean startProcessByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables) {

        List<Task> taskList = getTasksByBusKey(businessKey);

        if (CollectionUtils.isNotEmpty(taskList)) {
            LogUtil.getInstance(getClass()).error("启动流程异常：【业务数据：{}的流程已存在】", businessKey);
            throw new BusException("启动流程异常：【业务数据：" + businessKey + "的流程已存在】");
        }

        try {
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);

            LogUtil.getInstance(getClass()).debug("=========================启动流程输出============================");
            LogUtil.getInstance(getClass()).debug("流程实例ID\t:【{}】", processInstance.getId());
            LogUtil.getInstance(getClass()).debug("流程定义ID\t:【{}】", processInstance.getProcessDefinitionId());
            LogUtil.getInstance(getClass()).debug("流程定义KEY\t:【{}】", processInstance.getProcessDefinitionKey());
            LogUtil.getInstance(getClass()).debug("流程部署ID\t:【{}】", processInstance.getDeploymentId());
            LogUtil.getInstance(getClass()).debug("流程部署ID\t:【{}】", processInstance.getProcessInstanceId());
            LogUtil.getInstance(getClass()).debug("=========================启动流程输出============================");

            taskList = getTasksByBusKey(businessKey);

            return CollectionUtils.isNotEmpty(taskList);
        } catch (Exception e) {
            LogUtil.getInstance(getClass()).error("开启流程异常：【{}】", e.getMessage());
            throw new BusException(e.getMessage());
        }
    }

    @Override
    public boolean setAssignee(String businessKey, String assignee) {

        List<Task> taskList = getTasksByBusKey(businessKey);

        if (CollectionUtils.isEmpty(taskList)) {
            throw new BusException("未查询到关于【" + businessKey + "】相关的任务");
        }

        try {
            taskService.setAssignee(taskList.get(0).getId(), assignee);
            return true;
        } catch (Exception e) {
            LogUtil.getInstance(getClass()).error("设置代理人异常：【{}】", e.getMessage());
            throw new BusException(e.getMessage());
        }
    }

    @Override
    public List<CommentResponse> getCommentList(String businessKey) {
        List<Task> taskList = getTasksByBusKey(businessKey);
        if (CollectionUtils.isEmpty(taskList)) {
            throw new BusException("未查询到关于【" + businessKey + "】相关的任务");
        }

        List<Comment> comments = taskService.getProcessInstanceComments(taskList.get(0).getProcessInstanceId());

        return comments.stream().map(
                item -> new CommentResponse(item.getId(), item.getUserId(), item.getTime(), item.getTaskId(), item.getProcessInstanceId(), item.getType(), item.getFullMessage())
        ).collect(Collectors.toList());

    }

    @Override
    @Transactional(rollbackFor = BusException.class)
    public CompleteResponse completeByBusinessKey(String businessKey, Map<String, Object> map) {
        CompleteResponse completeResponse = new CompleteResponse(false, false, "");

        String currentUser = map.getOrDefault(VariableConstant.CURRENT_USER.name(), "").toString();
        if (StringUtil.isEmpty(currentUser)) {
            // 没有指定查询用户
            LogUtil.getInstance(getClass()).error("没有指定查询用户");
            throw new BusException("没有指定查询用户");
        }

        // 获取businessKey相关的业务的currentUser的任务列表
        List<TaskResponse> taskResponses = getTaskListByAssignee(businessKey, currentUser, false);

        if (CollectionUtils.isEmpty(taskResponses)) {
            // 未查询到当前任务
            LogUtil.getInstance(getClass()).error("未查询到关于【{}】下【{}】相关的任务", businessKey, currentUser);
            throw new BusException("未查询到关于【" + businessKey + "】下【" + currentUser + "】相关的任务");
        }

        if (taskResponses.size() != 1) {
            LogUtil.getInstance(getClass()).error("查询到关于【{}】下【{}】的多笔异常任务", businessKey, currentUser);
            throw new BusException("查询到关于【" + businessKey + "】下【" + currentUser + "】的多笔异常任务");
        }

        String comment = map.getOrDefault(VariableConstant.COMMENT.name(), "").toString();
        if (StringUtil.isNotEmpty(comment)) {
            taskService.addComment(taskResponses.get(0).getTaskId(), taskResponses.get(0).getProcessInstanceId(), comment);
        }
        try {
            // 完成任务
            taskService.complete(taskResponses.get(0).getTaskId(), map);
            completeResponse.setOk(true);

            List<Task> tasksByBusKey = getTasksByBusKey(businessKey);
            if (CollectionUtils.isEmpty(tasksByBusKey)) {
                // 任务已完成
                completeResponse.setFinished(true);
            } else {
                completeResponse.setTaskId(tasksByBusKey.get(0).getId());
            }
        } catch (Exception e) {
            LogUtil.getInstance(getClass()).error("提交任务异常：【{}】", e.getMessage());
            throw new BusException(e.getMessage());
        }
        return completeResponse;
    }

    @Override
    public List<TaskResponse> getTaskListByAssignee(String businessKey, String userId, boolean isActive) {
        // 获取businessKey相关的业务的currentUser的任务列表
        TaskQuery taskQuery = taskService.createTaskQuery()
                .orderByTaskCreateTime()
                .asc();

        if (StringUtil.isNotEmpty(userId)) {
            taskQuery = taskQuery.taskCandidateOrAssigned(userId);
        }

        if (StringUtil.isNotEmpty(businessKey)) {
            taskQuery = taskQuery.processInstanceBusinessKey(businessKey);
        }

        if (isActive) {
            taskQuery = taskQuery.active();
        }

        List<Task> tasks = taskQuery.list();

        return tasks.stream().map(
                item -> new TaskResponse(
                        item.getAssignee(),
                        item.getId(),
                        item.getName(),
                        item.getProcessDefinitionId(),
                        item.getProcessInstanceId(),
                        item.getExecutionId(),
                        item.getCreateTime(),
                        item.getProcessVariables(),
                        item.getTaskLocalVariables()
                )
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = BusException.class)
    public boolean revokeFlow(String processDefinitionKey, String businessKey, String userId, String reason) {

        boolean isRevoke = false;
        try {
            List<ProcessInstance> list = runtimeService.createProcessInstanceQuery()
                    .processInstanceBusinessKey(businessKey, processDefinitionKey)
                    .involvedUser(userId)
                    .list();

            list.forEach(item -> {
                runtimeService.deleteProcessInstance(item.getProcessInstanceId(), reason);
            });
            isRevoke = true;
        } catch (Exception e) {
            LogUtil.getInstance(getClass()).error("撤销任务异常：【{}】", e.getMessage());
            throw new BusException(e.getMessage());
        }

        return isRevoke;
    }

    @Override
    public List<HistoryResponse> getHistoryTaskList(String processDefinitionKey, String businessKey, String userId, Pagination pagination) {

        int firstResult = (pagination.getCurrentIndex() - 1) * pagination.getPageSize();

        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();

        if (StringUtil.isNotEmpty(processDefinitionKey)) {
            query = query.processDefinitionKey(processDefinitionKey);
        }

        if (StringUtil.isNotEmpty(businessKey)) {
            query = query.processInstanceBusinessKey(businessKey);
        }

        if (StringUtil.isNotEmpty(userId)) {
            query = query.taskAssignee(userId);
        }

        List<HistoricTaskInstance> list = query.listPage(firstResult, pagination.getPageSize());

        return list.stream().map(
                item -> new HistoryResponse(item.getAssignee(), item.getId(), item.getName(), item.getProcessDefinitionId(), item.getProcessInstanceId(), item.getExecutionId(), item.getCreateTime(), item.getProcessVariables(), item.getTaskLocalVariables(), item.getStartTime(), item.getEndTime(), item.getClaimTime())
        ).collect(Collectors.toList());
    }

    /**
     * 获取任务实例
     *
     * @param businessKey 业务关联键
     * @return List<Task>
     */
    private List<Task> getTasksByBusKey(String businessKey) {
        return taskService.createTaskQuery().processInstanceBusinessKey(businessKey).list();
    }
}
