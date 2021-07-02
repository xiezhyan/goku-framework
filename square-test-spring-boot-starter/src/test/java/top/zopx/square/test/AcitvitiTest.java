package top.zopx.square.test;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.zopx.starter.activiti.constant.VariableConstant;
import top.zopx.starter.activiti.entity.response.CommentResponse;
import top.zopx.starter.activiti.entity.response.CompleteResponse;
import top.zopx.starter.activiti.entity.response.HistoryResponse;
import top.zopx.starter.activiti.entity.response.TaskResponse;
import top.zopx.starter.activiti.service.IActivitiService;
import top.zopx.starter.activiti.service.IBusinessFlowService;
import top.zopx.starter.tools.basic.Pagination;
import top.zopx.starter.tools.tools.json.impl.FJsonUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @date 2021/6/30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AcitvitiTest {

    @Resource
    private IActivitiService activitiService;
    @Resource
    private IBusinessFlowService businessFlowService;

    @Resource
    private RepositoryService repositoryService;
    @Resource
    private TaskService taskService;

    String businessKey = "1";

    static Map<String, Object> map = new HashMap<String, Object>() {{
        put("type", "1");
        put("day", 1);
        put("startTime", new Date());
        put("endTime", new Date());
        put("reason", "个人元婴");
    }};

    @Test
    public void test01() {

        /**
         * 开启任务之后，进入到【发起审批】节点，在这里，通过businesskey设置代理人，也就是发起任务人本身
         *  businessKey 本身就是唯一不可重复的列
         */
//        businessFlowService.startProcessByKey("level", businessKey, null);
//        businessFlowService.setAssignee(businessKey, "1");

        /**
         * 设置需要传递的参数之后，进入到下一步审批流程【部门领导审批】
         *  同时选择下一步需要审批的人
         */
        map.put(VariableConstant.CURRENT_USER.name(), "1");
        CompleteResponse completeResponse = businessFlowService.completeByBusinessKey(businessKey, map);
        System.out.println("taskId:" + completeResponse.getTaskId());
        System.out.println("isOk:" + completeResponse.isOk());
        System.out.println("isFinished:" + completeResponse.isFinished());
        businessFlowService.setAssignee(businessKey, "1:1");
    }

    @Test
    public void test02() {
        /**
         * 1、通过代理人获取需要代办的事项
         */

        List<TaskResponse> list = businessFlowService.getTaskListByAssignee("", "1:1", true);

        for (TaskResponse taskResponse : list) {
            System.out.println("taskId: " + taskResponse.getTaskId());
            System.out.println("taskName: " + taskResponse.getTaskName());
        }


        /**
         * 2、通过该流程进行审批，然后让其进入到下一步

         map.put("leaderIsOk", 1);
         map.put(VariableConstant.CURRENT_USER.name(), "1:1");
         map.put(VariableConstant.COMMENT.name(), "无法通过");


         CompleteResponse completeResponse = businessFlowService.completeByBusinessKey(businessKey, map);
         System.out.println("taskId:" + completeResponse.getTaskId());
         System.out.println("isOk:" + completeResponse.isOk());
         System.out.println("isFinished:" + completeResponse.isFinished());
         if (map.getOrDefault("leaderIsOk", "").toString().equals("0")) {
         businessFlowService.setAssignee(businessKey, "1");
         } else {

         }
         */
    }


    @Test
    public void test03() {
        List<CommentResponse> commentList = businessFlowService.getCommentList(businessKey);

        commentList.forEach(item -> System.out.println(item.getFullMessage()));
    }

    @Test
    public void test05() {
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("level")
                .list();

        System.out.println(list.size());
    }

    @Test
    public void test06() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionKey("level").latestVersion().list();
        System.out.println("=========" + list.size() + "=====================");
        list.forEach(item -> {
            System.out.println(item.getDiagramResourceName());
            System.out.println(item.getId());
        });
    }

    @Test
    public void test04() {
        List<HistoryResponse> historyTaskList = businessFlowService.getHistoryTaskList("level", businessKey, "", new Pagination());

        System.out.println(FJsonUtil.INSTANCE.toJson(historyTaskList));
    }

    @Test
    public void test09() {
        boolean b = businessFlowService.revokeFlow("level", businessKey, "1", "填错了");
        System.out.println("b:" + b);
    }


    @Resource
    private CuratorFramework curatorFramework;


    @Test
    public void testLock() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, "/lock");
                try {
                    interProcessMutex.acquire();
                    System.out.println(Thread.currentThread().getName() + ": lock");
                    TimeUnit.SECONDS.sleep(2L);
                    System.out.println(Thread.currentThread().getName() + ": unlock");

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        interProcessMutex.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        while (true) {}
    }
}
