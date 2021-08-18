package top.zopx.starter.activiti.entity.response;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 历史记录
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/6/29
 */
public class HistoryResponse extends TaskResponse implements Serializable {

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 领取任务的时间
     */
    private Date claimTime;

    public HistoryResponse(String id, String name, String processDefinitionId, Map<String, Object> processVariables, Date startTime, Date endTime) {
        setTaskId(id);
        setTaskName(name);
        setProcessDefinitionId(processDefinitionId);
        setProcessVariables(processVariables);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public HistoryResponse(String assignee, String taskId, String taskName, String processDefinitionId, String processInstanceId, String executionId, Date createTime, Map<String, Object> processVariables, Map<String, Object> taskLocalVariables, Date startTime, Date endTime, Date claimTime) {
        super(assignee, taskId, taskName, processDefinitionId, processInstanceId, executionId, createTime, processVariables, taskLocalVariables);
        this.startTime = startTime;
        this.endTime = endTime;
        this.claimTime = claimTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getClaimTime() {
        return claimTime;
    }

    public void setClaimTime(Date claimTime) {
        this.claimTime = claimTime;
    }
}
