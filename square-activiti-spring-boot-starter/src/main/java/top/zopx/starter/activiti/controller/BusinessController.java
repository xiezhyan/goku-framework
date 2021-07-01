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
 * 业务相关接口
 *
 * @author mr.sanq
 * @date 2021/7/1
 */
@RestController
@RequestMapping("/business")
public class BusinessController {

    @Resource
    private IBusinessFlowService businessFlowService;

    @PostMapping("/start")
    public R<Boolean> startProcessByKey(@RequestBody ProcessRequest request) {
        return R.status(businessFlowService.startProcessByKey(request.getProcessDefinitionKey(), request.getBusinessKey(), request.getVariables()));
    }

    @PostMapping("/assgnee")
    public R<Boolean> setAssgnee(@RequestBody ProcessRequest request) {
        return R.status(businessFlowService.setAssignee(request.getBusinessKey(), request.getAssgnee()));
    }

    @GetMapping("/comment")
    public R<List<CommentResponse>> getComment(@RequestParam("businessKey") String businessKey) {
        return R.result(businessFlowService.getCommentList(businessKey));
    }

    @PostMapping("/completeByBusinessKey")
    public R<CompleteResponse> completeByBusinessKey(@RequestBody ProcessRequest request) {
        return R.result(businessFlowService.completeByBusinessKey(request.getBusinessKey(), request.getVariables()));
    }

    @GetMapping("/getTaskListByAssignee")
    public R<List<TaskResponse>> getTaskListByAssignee(
            @RequestParam("businessKey") String businessKey,
            @RequestParam("userId") String userId,
            @RequestParam(value = "isActive", required = false, defaultValue = "true") boolean isActive
    ) {
        return R.result(businessFlowService.getTaskListByAssignee(businessKey, userId, isActive));
    }

    @DeleteMapping("/revokeFlow")
    public R<Boolean> revokeFlow(@RequestBody RevokeFlowRequest request) {
        return R.status(businessFlowService.revokeFlow(request.getProcessDefinitionKey(), request.getBusinessKey(), request.getUserId(), request.getReason()));
    }

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
