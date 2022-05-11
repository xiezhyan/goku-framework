package top.zopx.starter.activiti.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import top.zopx.starter.activiti.entity.request.ModelRequest;
import top.zopx.starter.activiti.entity.response.ModelResponse;
import top.zopx.starter.activiti.service.IActivitiService;
import top.zopx.starter.tools.basic.Pagination;
import top.zopx.starter.tools.exceptions.BusException;
import top.zopx.starter.tools.tools.strings.StringUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 俗世游子
 * @date 2021/6/26
 * @email xiezhyan@126.com
 */
public class ActivitiServiceImpl implements IActivitiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiServiceImpl.class);

    @Resource
    private RepositoryService repositoryService;
    @Resource
    private TaskService taskService;
    @Resource
    private RuntimeService runtimeService;

    @Resource
    private ObjectMapper objectMapper;

    final BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    @Override
    @Transactional(rollbackFor = BusException.class)
    public String saveOrUpdate(String modelId, String tenantId, String category) {
        // 当前存在，直接跳转
        if (StringUtil.isNotEmpty(modelId)) {
            Model model = repositoryService.getModel(modelId);

            if (null != model) {
                return "/modeler.html?modelId=" + modelId;
            }
        }

        // 新建过程
        try {
            String modelName = "modelName";
            String modelKey = StringUtil.uuid();
            String description = "description";

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

            if (StringUtil.isNotBlank(category)) {
                modelData.setCategory(category);
            }
            if (StringUtil.isNotBlank(tenantId)) {
                modelData.setTenantId(tenantId);
            }

            //保存模型
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes(StandardCharsets.UTF_8));
            return "/modeler.html?modelId=" + modelData.getId();
        } catch (Exception e) {
            LOGGER.error("saveOrUpdate异常信息: 【{}】", e.getMessage());
            throw new BusException(e.getMessage());
        }
    }

    @Override
    public List<ModelResponse> getList(ModelRequest request, Pagination pagination) {

        ModelQuery query = repositoryService.createModelQuery();

        if (StringUtil.isNotEmpty(request.getName())) {
            // 按照name进行搜索
            query = query.modelNameLike("%" + request.getName() + "%");
        }

        if (StringUtil.isNotEmpty(request.getKey())) {
            // 按照key进行搜索
            query = query.modelKey(request.getKey());
        }

        if (StringUtil.isNotEmpty(request.getCategory())) {
            // 分类
            query = query.modelCategoryLike("%" + request.getCategory() + "%");
        }

        if (StringUtil.isNotEmpty(request.getTenantId())) {
            // 租户
            query = query.modelTenantId(request.getTenantId());
        }

        int firstResult = (pagination.getCurrentIndex() - 1) * pagination.getPageSize();
        List<Model> modelList = query.orderByModelId().asc().listPage(firstResult, pagination.getPageSize());

        return modelList.stream()
                .map(model ->
                        {
                            ModelResponse response = new ModelResponse(
                                    model.getId(),
                                    model.getName(),
                                    model.getKey(),
                                    model.getCategory(),
                                    model.getCreateTime(),
                                    model.getLastUpdateTime(),
                                    model.getMetaInfo(),
                                    model.getDeploymentId()
                            );
                            response.setVersion(model.getVersion());
                            return response;
                        }
                )
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = BusException.class)
    public boolean deploy(String modelId) {
        boolean isDeploy = false;

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
                    .category(model.getCategory())
                    .key(model.getKey())
                    .addBpmnModel(processName, bpmnModel)
                    .deploy();

            model.setDeploymentId(deploy.getId());
            repositoryService.saveModel(model);
            isDeploy = true;
        } catch (IOException e) {
            LOGGER.error("流程部署失败：【{}】", e.getMessage());
            throw new BusException(e.getMessage());
        }

        return isDeploy;
    }

    @Transactional(rollbackFor = BusException.class)
    @Override
    public boolean deleteByModelId(String modelId) {
        boolean isDelete = false;

        final Model model = repositoryService.getModel(modelId);
        if (null == model) {
            throw new BusException("当前流程不存在");
        }

        // 2、 检查当前流程图下是否存在正在执行的流程
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey(model.getKey())
                .list();

        if (CollectionUtils.isNotEmpty(list)) {
            throw new BusException("当前流程下存在正在执行的任务，不可被删除");
        }

        try {
            repositoryService.deleteDeployment(model.getDeploymentId(), true);
            repositoryService.deleteModel(modelId);
            isDelete = true;
        } catch (Exception e) {
            LOGGER.error("删除流程出现错误：【{}】", e.getMessage());
            throw new BusException(e.getMessage());
        }
        return isDelete;
    }

    @Override
    public ModelResponse getByProcessDefinitionId(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
        if (null == processDefinition) {
            throw new BusException("流程图不存在");
        }

        ModelResponse response = new ModelResponse();
        response.setName(response.getName());
        response.setKey(response.getKey());
        response.setVersion(response.getVersion());
        response.setCategory(response.getCategory());
        response.setDeploymentId(response.getDeploymentId());

        return response;
    }

    @Override
    public InputStream viewPic(String processDefinitionKey) {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).latestVersion().list();

        if (CollectionUtils.isEmpty(list)) {
            throw new BusException("没有查询到关于【"+processDefinitionKey+"】的部署流程");
        }

        ProcessDefinition processDefinition = list.get(0);

        return repositoryService.getResourceAsStream(processDefinition.getId(), processDefinition.getDiagramResourceName());
    }

    @Override
    public InputStream viewCurrentPic(String processDefinitionKey, String businessKey) {

        return null;
    }
}
