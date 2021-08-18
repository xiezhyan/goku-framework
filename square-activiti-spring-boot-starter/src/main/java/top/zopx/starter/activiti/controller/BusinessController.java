package top.zopx.starter.activiti.controller;

import org.springframework.web.bind.annotation.*;
import top.zopx.starter.activiti.entity.request.ProcessRequest;
import top.zopx.starter.activiti.entity.request.RevokeFlowRequest;
import top.zopx.starter.activiti.entity.response.CommentResponse;
import top.zopx.starter.activiti.entity.response.CompleteResponse;
import top.zopx.starter.activiti.entity.response.HistoryResponse;
import top.zopx.starter.activiti.entity.response.TaskResponse;
import top.zopx.starter.activiti.service.IBusinessFlowService;
import top.zopx.starter.tools.basic.Page;
import top.zopx.starter.tools.basic.Pagination;
import top.zopx.starter.tools.basic.R;

import javax.annotation.Resource;
import java.util.List;

/**
 * 实例操作
 *
 * @author mr.sanq
 * @date 2021/7/1
 */
@RestController
@RequestMapping("/business")
public class BusinessController {

    @Resource
    private IBusinessFlowService businessFlowService;

    /**
     * 启动流程
     *
     * @param request 启动参数
     * @return true：启动成功    false：启动失败
     */
    @PostMapping("/start")
    public R<Boolean> startProcessByKey(@RequestBody ProcessRequest request) {
        return R.status(businessFlowService.startProcessByKey(request.getProcessDefinitionKey(), request.getBusinessKey(), request.getVariables()));
    }

    /**
     * 设置代理人
     *
     * @param request 代理人参数
     * @return
     */
    @PostMapping("/assgnee")
    public R<Boolean> setAssgnee(@RequestBody ProcessRequest request) {
        return R.status(businessFlowService.setAssignee(request.getBusinessKey(), request.getAssgnee()));
    }

    /**
     * 查看评论列表
     *
     * @param businessKey 业务关联键
     * @return List<CommentResponse>
     */
    @GetMapping("/comment")
    public R<List<CommentResponse>> getComment(@RequestParam("businessKey") String businessKey) {
        return R.result(businessFlowService.getCommentList(businessKey));
    }

    /**
     * 提交任务
     *
     * @param request 提交参数
     * @return CompleteResponse
     */
    @PostMapping("/completeByBusinessKey")
    public R<CompleteResponse> completeByBusinessKey(@RequestBody ProcessRequest request) {
        return R.result(businessFlowService.completeByBusinessKey(request.getBusinessKey(), request.getVariables()));
    }

    /**
     * 待办任务
     *
     * @param businessKey 业务关联键
     * @param userId      当前用户
     * @param isActive    是否是活动状态
     * @return List<TaskResponse>
     */
    @GetMapping("/getTaskListByAssignee")
    public R<List<TaskResponse>> getTaskListByAssignee(
            @RequestParam("businessKey") String businessKey,
            @RequestParam("userId") String userId,
            @RequestParam(value = "isActive", required = false, defaultValue = "true") boolean isActive
    ) {
        return R.result(businessFlowService.getTaskListByAssignee(businessKey, userId, isActive));
    }

    /**
     * 撤销流程
     *
     * @param request 参数
     * @return Boolean true:撤销成功    false：撤销失败
     */
    @DeleteMapping("/revokeFlow")
    public R<Boolean> revokeFlow(@RequestBody RevokeFlowRequest request) {
        return R.status(businessFlowService.revokeFlow(request.getProcessDefinitionKey(), request.getBusinessKey(), request.getUserId(), request.getReason()));
    }

    /**
     * 历史流程
     *
     * @param processDefinitionKey 流程唯一标识
     * @param businessKey          业务关联键
     * @param userId               当前用户
     * @param currentIndex         页码
     * @param pageSize             当前展示条数
     * @return Page<HistoryResponse>
     */
    @GetMapping("/getHistoryTaskList")
    public R<Page<HistoryResponse>> getHistoryTaskList(
            @RequestParam(value = "processDefinitionKey", required = false) String processDefinitionKey,
            @RequestParam(value = "businessKey", required = false) String businessKey,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "currentIndex", required = false, defaultValue = "1") int currentIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        Pagination pagination = new Pagination();
        pagination.setCurrentIndex(currentIndex);
        pagination.setPageSize(pageSize);

        List<HistoryResponse> historyTaskList = businessFlowService.getHistoryTaskList(processDefinitionKey, businessKey, userId, pagination);

        return R.result(new Page<>(pagination, historyTaskList));
    }
}
