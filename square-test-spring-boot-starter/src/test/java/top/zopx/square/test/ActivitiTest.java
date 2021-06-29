package top.zopx.square.test;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import top.zopx.starter.activiti.service.IActivitiService;
import top.zopx.starter.tools.tools.json.impl.FJsonUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiezhongyan@chinasofti.com
 * @email xiezhongyan@chinasofti.com
 * @date 2021/6/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ActivitiTest {

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;

    @Resource
    private IActivitiService activitiService;

    @Test
    public void dd() {}

    @Test
    public void start() {
    }

    @Test
    public void getTaskList() {
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee("lisi")
                .list();

        for (Task task : list) {
            System.out.println("任务ID\t"	 + task.getId());
            System.out.println("任务名称\t" + task.getName());
            System.out.println("流程定义ID\t" + task.getProcessDefinitionId());
            System.out.println("流程实例ID\t"+task.getProcessInstanceId());
            System.out.println("执行对象ID\t"+task.getExecutionId());
            System.out.println("任务创办时间\t"+task.getCreateTime());
            System.out.println("参数\t"+ FJsonUtil.INSTANCE.toJson(task.getProcessVariables()));
            System.out.println("参数\t"+ FJsonUtil.INSTANCE.toJson(task.getTaskLocalVariables()));

            System.out.println("=======");
        }
    }

    @Test
    public void setGetVar() {
        Map<String, Object> variables = taskService.getVariables("5009");
        System.out.println(FJsonUtil.INSTANCE.toJson(variables));
    }
}
