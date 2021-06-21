package top.zopx.starter.activiti.controller.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.editor.language.json.converter.BpmnJsonConverterUtil;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.springframework.web.bind.annotation.*;
import top.zopx.starter.tools.basic.Page;
import top.zopx.starter.tools.basic.Pagination;
import top.zopx.starter.tools.basic.R;
import top.zopx.starter.tools.exceptions.BusException;
import top.zopx.starter.tools.tools.web.LogUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
     * 主要目的：跳转到可视化界面
     * @param request request
     * @param response response
     */
    @GetMapping("/creator")
    public void createModel(HttpServletRequest request, HttpServletResponse response){
        try{
            String modelName = "modelName";
            String modelKey = "modelKey";
            String description = "description";

            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

            RepositoryService repositoryService = processEngine.getRepositoryService();

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.set("stencilset", stencilSetNode);
            // 定义新模型
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, modelName);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(modelName);
            modelData.setKey(modelKey);

            //保存模型
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes(StandardCharsets.UTF_8));
            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());
        }catch (Exception e){
            LogUtil.getInstance(getClass()).error(e.getMessage());
        }
    }

    /**
     * 分页获取流程数据
     * @param pagination 分页条件
     * @return Page<Model>
     */
    @GetMapping("/list")
    public R<Page<Model>> getList(Pagination pagination) {
        int firstResult = (pagination.getCurrentIndex() - 1) * pagination.getPageSize();
        final ModelQuery query = repositoryService.createModelQuery();
        pagination.setTotalCount(query.count());

        return R.result(new Page<>(pagination, query.orderByCreateTime().desc().listPage(firstResult, pagination.getPageSize())));
    }

    /**
     * 发布流程
     * @param modelId 流程ID
     * @return 是否成功
     */
    @PostMapping("/deploy/{modelId}")
    public R<Boolean> deploy(@PathVariable("modelId") String modelId) {
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

            return R.status(true);
        } catch (IOException e) {
            LogUtil.getInstance(this.getClass()).error("流程部署失败：{}", e.getMessage());
            return R.status(false, e.getMessage());
        }
    }
}
