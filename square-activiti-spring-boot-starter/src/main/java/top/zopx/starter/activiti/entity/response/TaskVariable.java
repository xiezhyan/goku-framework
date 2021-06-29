package top.zopx.starter.activiti.entity.response;

import java.io.Serializable;
import java.util.Map;

/**
 * 任务参数
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/6/29
 */
public class TaskVariable implements Serializable {

    private String taskId;

    private String assignee;

    private String owner;

    private Map<String, Object> taskVar;

    private Map<String, Object> map;

    public TaskVariable() {
    }

    public TaskVariable(String taskId, String assignee, String owner, Map<String, Object> taskVar, Map<String, Object> map) {
        this.taskId = taskId;
        this.assignee = assignee;
        this.owner = owner;
        this.taskVar = taskVar;
        this.map = map;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Map<String, Object> getTaskVar() {
        return taskVar;
    }

    public void setTaskVar(Map<String, Object> taskVar) {
        this.taskVar = taskVar;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
