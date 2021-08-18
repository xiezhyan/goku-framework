package top.zopx.starter.activiti.entity.response;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/6/30
 */
public class CommentResponse  implements Serializable {

    /**
     * 编号
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 评论时间
     */
    private Date time;

    /**
     * 实例ID
     */
    private String taskId;

    /**
     * 引用做出此评论的流程实例
     */
    private String processInstanceId;

    /**
     * 对注释类型的引用
     */
    private String type;

    /**
     * 完整评论消息
     */
    private String fullMessage;

    public CommentResponse() {
    }

    public CommentResponse(String id, String userId, Date time, String taskId, String processInstanceId, String type, String fullMessage) {
        this.id = id;
        this.userId = userId;
        this.time = time;
        this.taskId = taskId;
        this.processInstanceId = processInstanceId;
        this.type = type;
        this.fullMessage = fullMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFullMessage() {
        return fullMessage;
    }

    public void setFullMessage(String fullMessage) {
        this.fullMessage = fullMessage;
    }
}
