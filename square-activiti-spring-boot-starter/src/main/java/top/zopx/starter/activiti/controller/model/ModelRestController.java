package top.zopx.starter.activiti.controller.model;

import org.springframework.web.bind.annotation.*;
import top.zopx.starter.activiti.IOConvert;
import top.zopx.starter.activiti.entity.request.ModelRequest;
import top.zopx.starter.activiti.entity.response.ModelResponse;
import top.zopx.starter.activiti.service.IActivitiService;
import top.zopx.starter.tools.basic.Page;
import top.zopx.starter.tools.basic.Pagination;
import top.zopx.starter.tools.basic.R;
import top.zopx.starter.tools.exceptions.BusException;
import top.zopx.starter.tools.tools.web.LogUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 流程操作
 *
 * @author mr.sanq
 * @date 2021/6/24
 */
@RestController
@RequestMapping("/model/rest")
public class ModelRestController {

    @Resource
    private IActivitiService activitiService;


    /**
     * 跳转到可视化界面
     *
     * @param modelId  流程编号
     * @param tenantId 租户
     * @param category 分类
     * @page /modeler.html?modelId=
     */
    @GetMapping("/creator")
    public void createModel(
            @RequestParam(value = "modelId", required = false) String modelId,
            @RequestParam(value = "tenantId", required = false) String tenantId,
            @RequestParam(value = "category", required = false) String category,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        final String redirectUrl = activitiService.saveOrUpdate(modelId, tenantId, category);

        try {
            response.sendRedirect(request.getContextPath() + redirectUrl);
        } catch (IOException e) {
            LogUtil.getInstance(getClass()).error("creator异常信息: {}", e.getMessage());
            throw new BusException(e.getMessage());
        }
    }

    /**
     * 分页获取流程数据
     *
     * @param name         流程名称
     * @param key          唯一标识
     * @param category     分类
     * @param tenantId     租户
     * @param currentIndex 页码
     * @param pageSize     显示条数
     * @return Page<ModelResponse>
     */
    @GetMapping("/list")
    public R<Page<ModelResponse>> getList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "tenantId", required = false) String tenantId,
            @RequestParam(value = "currentIndex", required = false, defaultValue = "1") int currentIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {

        Pagination pagination = new Pagination();
        pagination.setPageSize(pageSize);
        pagination.setCurrentIndex(currentIndex);

        ModelRequest request = new ModelRequest();
        request.setName(name);
        request.setKey(key);
        request.setCategory(category);
        request.setTenantId(tenantId);

        final List<ModelResponse> list = activitiService.getList(request, pagination);
        return R.result(new Page<>(pagination, list));
    }

    /**
     * 发布流程
     *
     * @param modelId 流程ID
     * @return Boolean true：成功  false：失败
     */
    @PostMapping("/deploy/{modelId}")
    public R<Boolean> deploy(@PathVariable("modelId") String modelId) {
        return R.status(activitiService.deploy(modelId));
    }

    /**
     * 删除流程
     *
     * @param modelId 流程ID
     * @return Boolean true：删除成功    false：删除失败
     */
    @DeleteMapping("/{modelId}")
    public R<Boolean> deleteByModelId(@PathVariable("modelId") String modelId) {
        return R.status(activitiService.deleteByModelId(modelId));
    }

    /**
     * 通过 processDefinitionId 获取当前Model
     *
     * @param processDefinitionId processDefinitionId
     * @return ModelResponse
     */
    @GetMapping("/{processDefinitionId}")
    public R<ModelResponse> getById(@PathVariable("processDefinitionId") String processDefinitionId) {
        return R.result(activitiService.getByProcessDefinitionId(processDefinitionId));
    }

    /**
     * 查看当前流程图
     * @param processDefinitionKey 流程定义key
     * @return base64编码后的字节数组
     */
    @GetMapping("/viewPic")
    public R<String> viewPic(@RequestParam("processDefinitionKey") String processDefinitionKey) {
        InputStream inputStream = activitiService.viewPic(processDefinitionKey);

        String data = "";
        if (null != inputStream) {
            try {
                data = IOConvert.INSTANCE.inputToString(inputStream);
            } catch (IOException e) {
                LogUtil.getInstance(getClass()).error("转换出现异常：【{}】", e.getMessage());
                throw new BusException(e.getMessage());
            }
        }

        return R.result(data);
    }
}
