package top.zopx.starter.activiti.controller.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.editor.language.json.converter.BpmnJsonConverterUtil;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.springframework.web.bind.annotation.*;
import top.zopx.starter.tools.basic.Page;
import top.zopx.starter.tools.basic.Pagination;
import top.zopx.starter.tools.exceptions.BusException;
import top.zopx.starter.tools.tools.web.LogUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author sanq.Yan
 * @date 2021/4/18
 */
@RestController
@RequestMapping("/activiti")
public class ModelRestController {

    @Resource
    private RepositoryService repositoryService;
    @Resource
    private ObjectMapper objectMapper;

    final BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    /**
     * 分页获取流程数据
     * @param pagination 分页条件
     * @return
     */
    @GetMapping("/list")
    public Page<Model> getList(Pagination pagination) {
        int firstResult = (pagination.getCurrentIndex() - 1) * pagination.getPageSize();
        final ModelQuery query = repositoryService.createModelQuery();
        pagination.setTotalCount(query.count());

        return new Page<>(pagination, query.orderByCreateTime().desc().listPage(firstResult, pagination.getPageSize()));
    }

    /**
     * 发布流程
     * @param modelId 流程ID
     * @return 是否成功
     */
    @PostMapping("/deploy/{modelId}")
    public Boolean deploy(@PathVariable("modelId") String modelId) {
        Model model = repositoryService.getModel(modelId);
        if (null == model) {
            throw new BusException("当前流程不存在");
        }

        final byte[] modelEditorSource = repositoryService.getModelEditorSource(modelId);
        if (null == modelEditorSource) {
            throw new BusException("流程图不存在");
        }

        try {
            final JsonNode jsonNode = objectMapper.readTree(modelEditorSource);
            final BpmnModel bpmnModel = bpmnJsonConverter.convertToBpmnModel(jsonNode);

            if (0 == bpmnModel.getProcesses().size()) {
                throw new BusException("当前流程图不存在流程节点");
            }

            String processName = model.getName() + ".bpmn20.xml";
            final Deployment deploy = repositoryService.createDeployment()
                    .name(processName)
                    .addBpmnModel(processName, bpmnModel)
                    .deploy();

            model.setDeploymentId(deploy.getId());
            repositoryService.saveModel(model);

            return true;
        } catch (IOException e) {
            LogUtil.getInstance(this.getClass()).error("流程部署失败：{}", e.getMessage());
            return false;
        }
    }
}
