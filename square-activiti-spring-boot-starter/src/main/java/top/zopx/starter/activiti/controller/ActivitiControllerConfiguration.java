package top.zopx.starter.activiti.controller;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import top.zopx.starter.activiti.controller.main.StencilsetRestResource;
import top.zopx.starter.activiti.controller.model.ModelEditorJsonRestResource;
import top.zopx.starter.activiti.controller.model.ModelRestController;
import top.zopx.starter.activiti.controller.model.ModelSaveRestResource;

/**
 * @author sanq.Yan
 * @date 2021/4/18
 */
@Component
@Import({
        StencilsetRestResource.class,
        ModelEditorJsonRestResource.class,
        ModelSaveRestResource.class,
        ModelRestController.class
})
public class ActivitiControllerConfiguration {
}
