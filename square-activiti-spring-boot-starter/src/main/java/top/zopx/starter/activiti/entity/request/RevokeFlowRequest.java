package top.zopx.starter.activiti.entity.request;

/**
 * @author sanq.Yan
 * @date 2021/7/1
 */
public class RevokeFlowRequest {

    /**
     * 流程图唯一标识
     */
    private String processDefinitionKey;
    /**
     * 业务关联键
     */
    private String businessKey;

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 撤销原因
     */
    private String reason;

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
