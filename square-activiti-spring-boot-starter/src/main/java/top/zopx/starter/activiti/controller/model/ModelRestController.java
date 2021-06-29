package top.zopx.starter.activiti.controller.model;

import org.springframework.web.bind.annotation.*;
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
import java.util.List;

/**
 * 提供自定义操作接口
 *
 * @author mr.sanq
 * @date 2021/6/24
 */
@RestController
@RequestMapping("/activiti")
public class ModelRestController {

    @Resource
    private IActivitiService activitiService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;


    /**
     * 跳转到可视化界面
     * <b>这里需要重点注意： 由于该接口需要重定向到modeler.html中，那么前端在调用该接口的时候，需要直接通过href来进行调用，无法进行axios操作</b>
     */
    @GetMapping("/creator")
    public void createModel(
            @RequestParam(value = "modelId", required = false) String modelId,
            @RequestParam(value = "tenantId", required = false) String tenantId,
            @RequestParam(value = "category", required = false) String category
    ) {
        final String redirectUrl = activitiService.saveOrUpdate(modelId, tenantId, category);

        try {
            response.sendRedirect(request.getContextPath() + redirectUrl);
        } catch (IOException e) {
            LogUtil.getInstance(getClass()).error("跳转出现异常：【{}】", e.getMessage());
            throw new BusException(e.getMessage());
        }
    }

    /**
     * 分页获取流程数据
     * @param pagination 分页对象
     * @param request 参数对象
     * @return Page<Model>
     */
    @GetMapping("/list")
    public R<Page<ModelResponse>> getList(ModelRequest request, Pagination pagination) {
        final List<ModelResponse> list = activitiService.getList(request, pagination);
        return R.result(new Page<>(pagination, list));
    }

    /**
     * 发布流程
     *
     * @param modelId 流程ID
     * @return 是否成功
     */
    @PostMapping("/deploy/{modelId}")
    public R<Boolean> deploy(@PathVariable("modelId") String modelId) {
        return R.status(activitiService.deploy(modelId));
    }

    /**
     * 删除流程
     *
     * @param modelId 流程ID
     * @return true | false
     */
    @DeleteMapping("/{modelId}")
    public R<Boolean> deleteByModelId(@PathVariable("modelId") String modelId) {
        return R.status(activitiService.deleteByModelId(modelId));
    }
}
