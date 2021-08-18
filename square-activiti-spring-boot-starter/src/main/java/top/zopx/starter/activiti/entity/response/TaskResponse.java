package top.zopx.starter.activiti.entity.response;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 代办任务
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/6/29
 */
public class TaskResponse implements Serializable {

    /**
     * 拥有者
     */
    private String assignee;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 流程定义ID
     */
    private String processDefinitionId;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 执行对象ID
     */
    private String executionId;

    /**
     * 任务创办时间
     */
    private Date createTime;

    /**
     * 流程参数
     */
    private Map<String, Object> processVariables;

    /**
     * 当前任务参数
     */
    private Map<String, Object> taskLocalVariables;

    public TaskResponse() {
    }

    public TaskResponse(String assignee, String taskId, String taskName, String processDefinitionId, String processInstanceId, String executionId, Date createTime, Map<String, Object> processVariables, Map<String, Object> taskLocalVariables) {
        this.assignee = assignee;
        this.taskId = taskId;
        this.taskName = taskName;
        this.processDefinitionId = processDefinitionId;
        this.processInstanceId = processInstanceId;
        this.executionId = executionId;
        this.createTime = createTime;
        this.processVariables = processVariables;
        this.taskLocalVariables = taskLocalVariables;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Map<String, Object> getProcessVariables() {
        return processVariables;
    }

    public void setProcessVariables(Map<String, Object> processVariables) {
        this.processVariables = processVariables;
    }

    public Map<String, Object> getTaskLocalVariables() {
        return taskLocalVariables;
    }

    public void setTaskLocalVariables(Map<String, Object> taskLocalVariables) {
        this.taskLocalVariables = taskLocalVariables;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
