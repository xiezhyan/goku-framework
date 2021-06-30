package top.zopx.starter.activiti.entity.response;

import java.io.Serializable;

/**
 * 提交任务返回
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/6/29
 */
public class CompleteResponse implements Serializable {

    private String taskId;

    private boolean isOk;

    private boolean isFinished;

    public CompleteResponse(boolean isOk, boolean isFinished, String taskId) {
        this.isOk = isOk;
        this.isFinished = isFinished;
        this.taskId = taskId;
    }

    public CompleteResponse() {
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
