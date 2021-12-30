package top.zopx.starter.activiti.entity.request;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author sanq.Yan
 * @date 2021/7/1
 */
public class ProcessRequest implements Serializable {

    /**
     * 流程图唯一标识
     * 
     */
    private String processDefinitionKey;
    /**
     * 业务关联键
     * 
     */
    private String businessKey;
    /**
     * 参数
     * 
     */
    private Map<String, Object> variables;

    /**
     * 代理人:[设置代理人接口必填]
     * 
     */
    private String assgnee;

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

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public String getAssgnee() {
        return assgnee;
    }

    public void setAssgnee(String assgnee) {
        this.assgnee = assgnee;
    }
}
