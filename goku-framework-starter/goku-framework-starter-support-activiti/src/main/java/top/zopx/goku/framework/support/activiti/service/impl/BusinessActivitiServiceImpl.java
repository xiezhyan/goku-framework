package top.zopx.goku.framework.support.activiti.service.impl;

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
import org.activiti.engine.task.Task;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zopx.goku.framework.support.activiti.constant.ActivitiErrorEnum;
import top.zopx.goku.framework.support.activiti.entity.dto.ModelDTO;
import top.zopx.goku.framework.support.activiti.entity.vo.ModelVO;
import top.zopx.goku.framework.support.activiti.service.IBusinessActivitiService;
import top.zopx.goku.framework.tools.entity.vo.Pagination;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import jakarta.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * @author Mr.Xie
 * @date 2021/6/26
 * @email xiezhyan@126.com
 */
@Service
public class BusinessActivitiServiceImpl implements IBusinessActivitiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessActivitiServiceImpl.class);

    @Resource
    private RepositoryService repositoryService;
    @Resource
    private TaskService taskService;

    @Resource
    private ObjectMapper objectMapper;

    final BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdate(String modelId, String tenantId, String category) {
        // 当前存在，直接跳转
        if (StringUtils.isNotEmpty(modelId)) {
            Model model = repositoryService.getModel(modelId);

            if (null != model) {
                return "/modeler.html?modelId=" + modelId;
            }
        }

        // 新建过程
        try {
            String modelName = "modelName";
            String modelKey = UUID.randomUUID().toString().replace("-", "").toLowerCase(Locale.ROOT);
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

            if (StringUtils.isNotBlank(category)) {
                modelData.setCategory(category);
            }
            if (StringUtils.isNotBlank(tenantId)) {
                modelData.setTenantId(tenantId);
            }

            //保存模型
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes(StandardCharsets.UTF_8));
            return "/modeler.html?modelId=" + modelData.getId();
        } catch (Exception e) {
            LOGGER.error("saveOrUpdate异常信息: 【{}】", e.getMessage());
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deploy(String modelId) {
        boolean isDeploy = false;

        Model model = repositoryService.getModel(modelId);
        if (null == model) {
            LOGGER.warn("model 流程图不存在");
            throw new BusException(ActivitiErrorEnum.MODEL_NOT_FOUND);
        }

        final byte[] modelEditorSource = repositoryService.getModelEditorSource(modelId);
        if (null == modelEditorSource) {
            LOGGER.warn("modelSource 流程图不存在");
            throw new BusException(ActivitiErrorEnum.MODEL_NOT_FOUND);
        }

        try {
            final JsonNode jsonNode = objectMapper.readTree(modelEditorSource);
            final BpmnModel bpmnModel = bpmnJsonConverter.convertToBpmnModel(jsonNode);

            if (bpmnModel.getProcesses().isEmpty()) {
                LOGGER.warn("modelSource 流程节点不存在");
                throw new BusException(ActivitiErrorEnum.MODEL_NOT_FOUND);
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
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }

        return isDeploy;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteByModelId(String modelId) {
        boolean isDelete = false;

        final Model model = repositoryService.getModel(modelId);
        if (null == model) {
            LOGGER.warn("model 当前流程不存在");
            throw new BusException(ActivitiErrorEnum.MODEL_NOT_FOUND);
        }

        // 2、 检查当前流程图下是否存在正在执行的流程
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey(model.getKey())
                .list();

        if (CollectionUtils.isNotEmpty(list)) {
            LOGGER.warn("当前流程下存在正在执行的任务，不可被删除");
            throw new BusException(ActivitiErrorEnum.MODEL_NOT_DELETE);
        }

        try {
            repositoryService.deleteDeployment(model.getDeploymentId(), true);
            repositoryService.deleteModel(modelId);
            isDelete = true;
        } catch (Exception e) {
            LOGGER.error("删除流程出现错误：【{}】", e.getMessage());
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
        return isDelete;
    }

    @Override
    public List<ModelVO> getList(ModelDTO request, Pagination pagination) {
        ModelQuery query = repositoryService.createModelQuery();

        if (StringUtils.isNotEmpty(request.getName())) {
            // 按照name进行搜索
            query = query.modelNameLike("%" + request.getName() + "%");
        }

        if (StringUtils.isNotEmpty(request.getKey())) {
            // 按照key进行搜索
            query = query.modelKey(request.getKey());
        }

        if (StringUtils.isNotEmpty(request.getCategory())) {
            // 分类
            query = query.modelCategoryLike("%" + request.getCategory() + "%");
        }

        if (StringUtils.isNotEmpty(request.getTenantId())) {
            // 租户
            query = query.modelTenantId(request.getTenantId());
        }

        int firstResult = (pagination.getCurrentIndex() - 1) * pagination.getPageSize();
        List<Model> modelList = query.orderByModelId().asc().listPage(firstResult, pagination.getPageSize());

        return modelList.stream()
                .map(model ->
                        {
                            ModelVO response = new ModelVO(
                                    model.getId(),
                                    model.getName(),
                                    model.getKey(),
                                    model.getCategory(),
                                    model.getCreateTime(),
                                    model.getLastUpdateTime(),
                                    model.getMetaInfo(),
                                    model.getDeploymentId(),
                                    model.getTenantId()
                            );
                            response.setVersion(model.getVersion());
                            return response;
                        }
                )
                .toList();
    }
}
